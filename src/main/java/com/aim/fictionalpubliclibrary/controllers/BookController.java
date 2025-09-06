package com.aim.fictionalpubliclibrary.controllers;

import com.aim.fictionalpubliclibrary.dtos.BookRequestDTO;
import com.aim.fictionalpubliclibrary.dtos.BookResponseDTO;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.services.BookService;
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
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = bookService.getAllBooks()
                .stream()
                .map(BookResponseDTO::fromBook)
                .toList();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        BookRequestDTO.validate(bookRequestDTO);
        Book bookToBeSaved = BookRequestDTO.toBook(bookRequestDTO);
        Book book = bookService.createBook(bookToBeSaved);
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                      @RequestBody BookRequestDTO bookRequestDTO) {
        Book book = bookService.updateBook(id, BookRequestDTO.toBook(bookRequestDTO));
        BookResponseDTO bookResponseDTO = BookResponseDTO.fromBook(book);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
