package com.aim.fictionalpubliclibrary.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a Book in the library.
 */
@Getter
@Setter
@Entity
public class Book extends BaseModel {
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;

    public static Book updateBook(Book book, Book updatedBook) {
        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getIsbn() != null) {
            book.setIsbn(updatedBook.getIsbn());
        }
        if (updatedBook.getPublishedYear() != 0) {
            book.setPublishedYear(updatedBook.getPublishedYear());
        }
        if (updatedBook.getGenre() != null) {
            book.setGenre(updatedBook.getGenre());
        }
        return book;
    }
}
