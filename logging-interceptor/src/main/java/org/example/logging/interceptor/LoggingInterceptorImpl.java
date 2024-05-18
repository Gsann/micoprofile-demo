package org.example.logging.interceptor;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Interceptor
@Dependent
@LoggingInterceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptorImpl {

    private final String REGEX = "LogIntercept";

    private final Logger logger = LoggerFactory.getLogger(REGEX);

    @AroundInvoke
    public Object interceptLogging(InvocationContext ctx) throws Exception {

        final String LOG_REGEX = "[" + REGEX + "] ";
        try {
            // ターゲットは、CDIのクライアントプロキシなので、スーパークラスを取得。
            final String classAndMethod = ctx.getTarget().getClass()
                    .getSuperclass().getName()
                    + "#" + ctx.getMethod().getName();

            // メソッドの実行
            // メソッド開始前のログ
            logger.debug(LOG_REGEX + classAndMethod + " start.");
            final Object ret = ctx.proceed();
            // メソッド終了後のログ
            logger.debug(LOG_REGEX + classAndMethod + " end.");
            if (Objects.nonNull(ret)) {
                logger.debug(LOG_REGEX + classAndMethod + " result:" + ret.toString());
            }
            return ret;
        } catch(Exception e) {
            // 例外のログを出したら、例外はそのまま再スローする。
            // トランザクションインターセプターの内部で処理されるので、
            // ここでは根本例外が出る。
            logger.error(LOG_REGEX + e.getMessage(), e);
            throw e;
        }
    }
}
