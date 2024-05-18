package org.example.service.b.domain.repository;

import org.example.service.b.domain.model.Book;

public interface IBookRepository {
    public Book findById(String bookId);
}
