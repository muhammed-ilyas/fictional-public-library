package com.aim.fictionalpubliclibrary.repositories;

import com.aim.fictionalpubliclibrary.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findById_WhenBookExists_ReturnsBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");
        
        // Save the book and get the persisted entity with ID
        Book savedBook = entityManager.persistAndFlush(book);
        
        // Act
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        
        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
        assertEquals("Test Author", foundBook.get().getAuthor());
        assertEquals("1234567890", foundBook.get().getIsbn());
    }
    
    @Test
    void findById_WhenBookDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        long nonExistentId = 999L;
        
        // Act
        Optional<Book> foundBook = bookRepository.findById(nonExistentId);
        
        // Assert
        assertFalse(foundBook.isPresent());
    }
    
    @Test
    void findAll_WhenBooksExist_ReturnsAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");
        book1.setIsbn("1111111111");
        book1.setPublishedYear(2021);
        book1.setGenre("Fiction");
        
        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("2222222222");
        book2.setPublishedYear(2022);
        book2.setGenre("Non-Fiction");
        
        entityManager.persistAndFlush(book1);
        entityManager.persistAndFlush(book2);
        
        // Act
        List<Book> books = bookRepository.findAll();
        
        // Assert
        assertEquals(2, books.size());
    }
    
    @Test
    void save_WhenBookIsValid_PersistsAndReturnsBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("New Author");
        book.setIsbn("9876543210");
        book.setPublishedYear(2025);
        book.setGenre("Sci-Fi");
        
        // Act
        Book savedBook = bookRepository.save(book);
        
        // Assert
        assertEquals("New Book", savedBook.getTitle());
        
        // Verify it's in the database
        Book foundBook = entityManager.find(Book.class, savedBook.getId());
        assertNotNull(foundBook);
        assertEquals("New Book", foundBook.getTitle());
    }
    
    @Test
    void delete_WhenBookExists_RemovesBookFromDatabase() {
        // Arrange
        Book book = new Book();
        book.setTitle("Book to Delete");
        book.setAuthor("Delete Author");
        book.setIsbn("5555555555");
        book.setPublishedYear(2020);
        book.setGenre("Mystery");
        
        Book savedBook = entityManager.persistAndFlush(book);
        Long bookId = savedBook.getId();
        
        // Verify book is in the database
        assertNotNull(entityManager.find(Book.class, bookId));
        
        // Act
        bookRepository.delete(savedBook);
        entityManager.flush();
        
        // Assert
        assertNull(entityManager.find(Book.class, bookId));
    }
}
