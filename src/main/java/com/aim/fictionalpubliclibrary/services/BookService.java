package com.aim.fictionalpubliclibrary.services;

import com.aim.fictionalpubliclibrary.models.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book createBook(Book bookToBeSaved);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);
}
