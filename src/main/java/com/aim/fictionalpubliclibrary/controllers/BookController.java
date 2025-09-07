package com.aim.fictionalpubliclibrary.controllers;

import com.aim.fictionalpubliclibrary.dtos.BookRequestDTO;
import com.aim.fictionalpubliclibrary.dtos.BookResponseDTO;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.services.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@Log4j2
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * API to get all books in the library
     * target: /v1/books
     * Method: GET
     * @return List of BookResponseDTO
     * Steps:
     * 1. Call the service layer to get all books
     * 2. Convert the list of Book entities to list of BookResponseDTO
     * 3. Return the list of BookResponseDTO with HTTP status 200 (OK)
     */
    @GetMapping()
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        log.info("Fetching all books from the library");
        List<BookResponseDTO> books = bookService.getAllBooks()
                .stream()
                .map(BookResponseDTO::fromBook)
                .toList();
        return ResponseEntity.ok(books);
    }

    /**
     * API to create a new book in the library
     * target: /v1/books
     * Method: POST
     * @param bookRequestDTO BookRequestDTO
     * @return BookResponseDTO
     * Steps:
     * 1. Validate the input BookRequestDTO
     * 2. Convert BookRequestDTO to Book entity
     * 3. Call the service layer to create the book
     * 4. Convert the created Book entity to BookResponseDTO
     * 5. Return the BookResponseDTO with HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        log.info("Creating a new book in the library: {}", bookRequestDTO);
        log.debug("Validating the book request DTO");
        BookRequestDTO.validate(bookRequestDTO);
        log.debug("Validation successful");
        Book bookToBeSaved = BookRequestDTO.toBook(bookRequestDTO);
        Book book = bookService.createBook(bookToBeSaved);
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.CREATED);
    }

    /**
     * API to get a book by its ID
     * target: /v1/books/{id}
     * Method: GET
     * @param id Long
     * @return BookResponseDTO
     * Steps:
     * 1. Call the service layer to get the book by ID
     * 2. Convert the Book entity to BookResponseDTO
     * 3. Return the BookResponseDTO with HTTP status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        log.info("Fetching book with ID: {}", id);
        Book book = bookService.getBookById(id);
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    /**
     * API to update a book by its ID
     * target: /v1/books/{id}
     * Method: PUT
     * @param id Long
     * @param bookRequestDTO BookRequestDTO
     * @return BookResponseDTO
     * Steps:
     * 1. Convert BookRequestDTO to Book entity
     * 2. Call the service layer to update the book by ID
     * 3. Convert the updated Book entity to BookResponseDTO
     * 4. Return the BookResponseDTO with HTTP status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                      @RequestBody BookRequestDTO bookRequestDTO) {
        log.info("Updating book with ID: {}", id);
        Book book = bookService.updateBook(id, BookRequestDTO.toBook(bookRequestDTO));
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    /**
     * API to delete a book by its ID
     * target: /v1/books/{id}
     * Method: DELETE
     * @param id Long
     * @return ResponseEntity with HTTP status 204 (No Content)
     * Steps:
     * 1. Call the service layer to delete the book by ID
     * 2. Return HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
