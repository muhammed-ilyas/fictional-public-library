package com.aim.fictionalpubliclibrary.dtos;

import com.aim.fictionalpubliclibrary.exceptions.InvalidInputException;
import com.aim.fictionalpubliclibrary.models.Book;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/** Data Transfer Object for Book requests. */
@Data
@Builder
public class BookRequestDTO {
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;

    /** Converts a BookRequestDTO to a Book entity.
     *
     * @param bookRequestDTO the BookRequestDTO to convert
     * @return the corresponding Book entity
     */
    public static Book toBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setPublishedYear(bookRequestDTO.getPublishedYear());
        book.setGenre(bookRequestDTO.getGenre());
        return book;
    }

    /** Validates the fields of a BookRequestDTO.
     *
     * @param bookRequestDTO the BookRequestDTO to validate
     * @throws InvalidInputException if any required field is missing or invalid
     */
    public static void validate(BookRequestDTO bookRequestDTO) {
        if (bookRequestDTO == null) {
            throw new InvalidInputException("BookRequestDTO cannot be null");
        }
        List<String> errors = new ArrayList<>();
        if (bookRequestDTO.getTitle() == null || bookRequestDTO.getTitle().isEmpty()) {
            errors.add("Title is required");
        }
        if (bookRequestDTO.getAuthor() == null || bookRequestDTO.getAuthor().isEmpty()) {
            errors.add("Author is required");
        }
        if (bookRequestDTO.getIsbn() == null || bookRequestDTO.getIsbn().isEmpty()) {
            errors.add("ISBN is required");
        }
        if (bookRequestDTO.getPublishedYear() <= 0) {
            errors.add("Published year must be a positive integer");
        }
        if (bookRequestDTO.getGenre() == null || bookRequestDTO.getGenre().isEmpty()) {
            errors.add("Genre is required");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputException(String.join(", ", errors));
        }
    }
}
