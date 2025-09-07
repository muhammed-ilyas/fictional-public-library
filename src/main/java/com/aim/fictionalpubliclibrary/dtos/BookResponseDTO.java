package com.aim.fictionalpubliclibrary.dtos;

import com.aim.fictionalpubliclibrary.models.Book;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for sending book details in responses.
 */
@Data
@Builder
public class BookResponseDTO {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;

    /**
     * Converts a Book entity to a BookResponseDTO.
     *
     * @param book the Book entity to convert
     * @return the corresponding BookResponseDTO
     */
    public static BookResponseDTO fromBook(Book book) {
        if (book == null) {
            return null;
        }
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre())
                .build();
    }
}
