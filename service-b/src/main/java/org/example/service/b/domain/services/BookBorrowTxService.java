package org.example.service.b.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.b.domain.model.BookBorrowTx;
import org.example.service.b.domain.repository.IBookBorrowTxRepository;
import org.example.service.b.domain.repository.IBookRepository;
import org.example.service.b.domain.repository.IUserRepository;

import java.time.LocalDateTime;

@LoggingInterceptor
@ApplicationScoped
public class BookBorrowTxService {

    @Inject
    private IUserRepository userRepository;

    @Inject
    private IBookRepository bookRepository;

    @Inject
    private IBookBorrowTxRepository txRepository;

    public void create(String bookId, String email, boolean txType) {
        try {
            var user = userRepository.findByEmail(email);
            var book = bookRepository.findById(bookId);
            var createDateTime = LocalDateTime.now();
            var tx = BookBorrowTx.create(createDateTime, txType, user, book);
            txRepository.add(tx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
