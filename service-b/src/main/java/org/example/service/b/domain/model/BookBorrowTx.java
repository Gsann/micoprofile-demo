package org.example.service.b.domain.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Schema(name="BookBorrowTx")
public class BookBorrowTx {

    @Schema(required = true, description = "PK Book id is UUID")
    private String bookId;
    @Schema(required = true, description = "Create Date Time")
    private LocalDateTime createDatetime;
    @Schema(required = true, description = "Tx Type. true is borrow, false is return")
    private boolean txType;
    @Schema(required = true, description = "User")
    private User user;
    @Schema(required = true, description = "Book")
    private Book book;

    public String getBookId() {
        return bookId;
    }
    private void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }
    public Instant getCreateDatetimeForTimeStamp() {
        return createDatetime.toInstant(ZoneOffset.UTC);
    }
    private void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }
    public boolean getTxType() {
        return txType;
    }
    private void setTxType(boolean txType) {
        this.txType = txType;
    }
    public User getUser() {
        return user;
    }
    private void setUser(User user) {
        this.user = user;
    }
    public Book getBook() {
        return book;
    }
    private void setBook(Book book) {
        this.book = book;
    }

    public static BookBorrowTx create(LocalDateTime createDatetime,
                                      boolean txType,
                                      User user,
                                      Book book) {

        if (user.isNonActive()) {
            throw new IllegalArgumentException("User is not active");
        }

        BookBorrowTx bookBorrowTx = new BookBorrowTx();
        bookBorrowTx.setBookId(book.getId());
        bookBorrowTx.setCreateDatetime(createDatetime);
        bookBorrowTx.setTxType(txType);
        bookBorrowTx.setUser(user);
        bookBorrowTx.setBook(book);
        return bookBorrowTx;
    }

}
