package org.example.service.b.domain.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

@Schema(name="Book")
public class Book {

    @Schema(required = true, description = "Book id is UUID")
    private String id;
    @Schema(required = true, description = "Book title")
    private String title;
    @Schema(required = true, description = "Book author")
    private String author;

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    public static Book newBook(String id, String title, String author) {

        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("id or title or author cannot be empty");
        }

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        return book;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("author", author);
        return map;
    }
}
