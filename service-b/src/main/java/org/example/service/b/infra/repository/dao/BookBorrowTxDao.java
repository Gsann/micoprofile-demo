package org.example.service.b.infra.repository.dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.b.domain.model.BookBorrowTx;
import org.example.service.b.domain.repository.IBookBorrowTxRepository;

import java.net.InetSocketAddress;

@LoggingInterceptor
@ApplicationScoped
public class BookBorrowTxDao implements IBookBorrowTxRepository {

    @ConfigProperty(name = "cassandra.host")
    @Inject
    private String cassandraHost;

    @ConfigProperty(name = "cassandra.port")
    @Inject
    private Integer cassandraPort;

    @ConfigProperty(name = "demo.keyspace")
    @Inject
    private String keyspaceName;

    private static final String BOOK_BORROW_TX_TABLE = "book_borrow_tx";

    public BookBorrowTxDao() {
    }

    @Override
    public void add(BookBorrowTx tx) {


        try (var cqlSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(cassandraHost, cassandraPort))
                .withKeyspace(keyspaceName)
                .withLocalDatacenter("datacenter1")
                .build()) {

            var statement = cqlSession.prepare(QueryBuilder.insertInto(BOOK_BORROW_TX_TABLE)
                    .value("book_id", QueryBuilder.bindMarker())
                    .value("create_datetime", QueryBuilder.bindMarker())
                    .value("tx_type", QueryBuilder.bindMarker())
                    .value("user", QueryBuilder.bindMarker())
                    .value("book", QueryBuilder.bindMarker())
                    .build());

            var rs = cqlSession.execute(
                    statement.bind(
                            tx.getBookId(),
                            tx.getCreateDatetimeForTimeStamp(),
                            tx.getTxType(),
                            tx.getUser().toMap(),
                            tx.getBook().toMap()
                    )
            );

            if (!rs.wasApplied()) {
                throw new IllegalArgumentException("Book borrow tx already exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
