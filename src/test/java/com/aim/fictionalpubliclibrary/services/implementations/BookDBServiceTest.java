package com.aim.fictionalpubliclibrary.services.implementations;

import com.aim.fictionalpubliclibrary.exceptions.ResourceNotFoundException;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDBServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookDBService bookDBService;

    private Book book;
    private List<Book> bookList;
    private final Long bookId = 1L;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");

        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    void getAllBooks_WhenBooksExist_ReturnsAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(bookList);

        // Act
        List<Book> result = bookDBService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookId, result.get(0).getId());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_WhenNoBooksExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookDBService.getAllBooks());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_WhenBookExists_ReturnsBook() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book result = bookDBService.getBookById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookDBService.getBookById(bookId));
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void createBook_WhenBookIsValid_ReturnsCreatedBook() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book result = bookDBService.createBook(book);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_WhenBookExists_ReturnsUpdatedBook() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Book result = bookDBService.updateBook(bookId, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        // Fields not included in the update should remain the same
        assertEquals("1234567890", result.getIsbn());
        assertEquals(2023, result.getPublishedYear());
        assertEquals("Fiction", result.getGenre());
        
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookDBService.updateBook(bookId, book));
        
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_WhenBookExists_DeletesBook() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        // Act
        bookDBService.deleteBook(bookId);

        // Assert
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_WhenBookDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookDBService.deleteBook(bookId));
        
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).delete(any(Book.class));
    }
}
