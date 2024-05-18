package org.example.service.a.domain.services;

import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.model.Book;
import org.example.service.a.domain.repositoty.IBookRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@LoggingInterceptor
@ApplicationScoped
public class BookService {

    @Inject
    private IBookRepository bookRepository;

    public List<Book> getList() {
        return bookRepository.findAll();
    }

    public Book getById(String id) {
         var bookOpt = bookRepository.findById(id);
         if (bookOpt.isEmpty()) {
             throw new RuntimeException();
         }
        return bookOpt.get();
    }
}
