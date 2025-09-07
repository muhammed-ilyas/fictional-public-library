package com.aim.fictionalpubliclibrary.services.implementations;

import com.aim.fictionalpubliclibrary.exceptions.ResourceNotFoundException;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.repositories.BookRepository;
import com.aim.fictionalpubliclibrary.services.BookService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookDBService implements BookService {

    private final BookRepository bookRepository;

    public BookDBService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Get all books from the database.
     * Steps:
     * 1. Call the repository layer to get all books.
     * 2. If the list is empty, throw ResourceNotFoundException.
     * 3. Return the list of books.
     * @return List of Book
     */
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (CollectionUtils.isEmpty(books)) {
            throw new ResourceNotFoundException("Books", "All", "All");
        }
        return books;
    }

    /**
     * Get a book by its ID.
     * Steps:
     * 1. Call the repository layer to get the book by ID.
     * 2. If the book is not found, throw ResourceNotFoundException.
     * 3. Return the book.
     * @param id Long
     * @return Book
     */
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
    }

    /**
     * Create a new book in the database.
     * Steps:
     * 1. Call the repository layer to save the book.
     * 2. Return the saved book.
     * @param bookToBeSaved Book
     * @return Book
     */
    @Override
    public Book createBook(Book bookToBeSaved) {
        return bookRepository.save(bookToBeSaved);
    }

    /**
     * Update an existing book in the database.
     * Steps:
     * 1. Call the repository layer to get the existing book by ID.
     * 2. If the book is not found, throw ResourceNotFoundException.
     * 3. Update the existing book with the new values.
     * 4. Call the repository layer to save the updated book.
     * 5. Return the updated book.
     * @param id Long
     * @param bookToBeUpdated Book
     * @return Book
     */
    @Override
    public Book updateBook(Long id, Book bookToBeUpdated) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
        Book updatedBook = Book.updateBook(existingBook, bookToBeUpdated);
        return bookRepository.save(updatedBook);
    }

    /**
     * Delete a book by its ID.
     * Steps:
     * 1. Call the repository layer to get the existing book by ID.
     * 2. If the book is not found, throw ResourceNotFoundException.
     * 3. Call the repository layer to delete the book.
     * @param id Long
     */
    @Override
    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book", "id", id)
                );
        bookRepository.delete(existingBook);
    }

}
