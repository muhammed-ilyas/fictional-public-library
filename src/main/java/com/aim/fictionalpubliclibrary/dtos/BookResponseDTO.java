package com.aim.fictionalpubliclibrary.dtos;

import com.aim.fictionalpubliclibrary.models.Book;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;

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
