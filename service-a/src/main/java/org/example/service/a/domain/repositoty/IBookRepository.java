package org.example.service.a.domain.repositoty;

import org.example.service.a.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    public List<Book> findAll();
    public Optional<Book> findById(String id);
}
