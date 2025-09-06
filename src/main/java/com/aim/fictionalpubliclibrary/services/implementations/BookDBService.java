package com.aim.fictionalpubliclibrary.services.implementations;

import com.aim.fictionalpubliclibrary.exceptions.ResourceNotFoundException;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.repositories.BookRepository;
import com.aim.fictionalpubliclibrary.services.BookService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("bookDBService")
public class BookDBService implements BookService {

    private final BookRepository bookRepository;

    public BookDBService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (CollectionUtils.isEmpty(books)) {
            throw new ResourceNotFoundException("Books", "All", "All");
        }
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
    }

    @Override
    public Book createBook(Book bookToBeSaved) {
        return bookRepository.save(bookToBeSaved);
    }

    @Override
    public Book updateBook(Long id, Book bookToBeUpdated) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
        Book updatedBook = Book.updateBook(existingBook, bookToBeUpdated);
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
        bookRepository.delete(existingBook);
    }

}
