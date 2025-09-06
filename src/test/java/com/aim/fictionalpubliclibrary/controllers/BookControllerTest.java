package com.aim.fictionalpubliclibrary.controllers;

import com.aim.fictionalpubliclibrary.dtos.BookRequestDTO;
import com.aim.fictionalpubliclibrary.dtos.BookResponseDTO;
import com.aim.fictionalpubliclibrary.models.Book;
import com.aim.fictionalpubliclibrary.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;
    private BookRequestDTO bookRequestDTO;
    private BookResponseDTO bookResponseDTO;
    private List<Book> bookList;
    private final Long bookId = 1L;

    @BeforeEach
    void setUp() {
        // Setup Book entity
        book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");

        // Setup BookRequestDTO
        bookRequestDTO = BookRequestDTO.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publishedYear(2023)
                .genre("Fiction")
                .build();
        // Mock static methods will be handled in each test

        // Setup BookResponseDTO
        bookResponseDTO = BookResponseDTO.builder()
                .id(bookId)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publishedYear(2023)
                .genre("Fiction")
                .build();

        // Setup Book list
        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    void getAllBooks_WhenBooksExist_ReturnsOkResponse() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(bookList);

        try (MockedStatic<BookResponseDTO> mockedStatic = mockStatic(BookResponseDTO.class)) {
            mockedStatic.when(() -> BookResponseDTO.fromBook(any(Book.class))).thenReturn(bookResponseDTO);

            // Act
            ResponseEntity<List<BookResponseDTO>> response = bookController.getAllBooks();

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(1, response.getBody().size());
            assertEquals(bookId, response.getBody().get(0).getId());
            assertEquals("Test Book", response.getBody().get(0).getTitle());

            verify(bookService, times(1)).getAllBooks();
            mockedStatic.verify(() -> BookResponseDTO.fromBook(any(Book.class)), times(1));
        }
    }

    @Test
    void createBook_WhenBookRequestIsValid_ReturnsCreatedResponse() {
        // Arrange
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        try (MockedStatic<BookRequestDTO> mockedStaticRequest = mockStatic(BookRequestDTO.class);
             MockedStatic<BookResponseDTO> mockedStaticResponse = mockStatic(BookResponseDTO.class)) {

            mockedStaticRequest.when(() -> BookRequestDTO.validate(any(BookRequestDTO.class))).then(invocation -> null);
            mockedStaticRequest.when(() -> BookRequestDTO.toBook(any(BookRequestDTO.class))).thenReturn(book);
            mockedStaticResponse.when(() -> BookResponseDTO.fromBook(any(Book.class))).thenReturn(bookResponseDTO);

            // Act
            ResponseEntity<BookResponseDTO> response = bookController.createBook(bookRequestDTO);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(bookId, response.getBody().getId());
            assertEquals("Test Book", response.getBody().getTitle());

            verify(bookService, times(1)).createBook(any(Book.class));
            mockedStaticRequest.verify(() -> BookRequestDTO.validate(any(BookRequestDTO.class)), times(1));
            mockedStaticRequest.verify(() -> BookRequestDTO.toBook(any(BookRequestDTO.class)), times(1));
            mockedStaticResponse.verify(() -> BookResponseDTO.fromBook(any(Book.class)), times(1));
        }
    }

    @Test
    void getBookById_WhenBookExists_ReturnsOkResponse() {
        // Arrange
        when(bookService.getBookById(bookId)).thenReturn(book);

        try (MockedStatic<BookResponseDTO> mockedStatic = mockStatic(BookResponseDTO.class)) {
            mockedStatic.when(() -> BookResponseDTO.fromBook(any(Book.class))).thenReturn(bookResponseDTO);

            // Act
            ResponseEntity<BookResponseDTO> response = bookController.getBookById(bookId);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(bookId, response.getBody().getId());
            assertEquals("Test Book", response.getBody().getTitle());

            verify(bookService, times(1)).getBookById(bookId);
            mockedStatic.verify(() -> BookResponseDTO.fromBook(any(Book.class)), times(1));
        }
    }

    @Test
    void updateBook_WhenBookExists_ReturnsOkResponse() {
        // Arrange
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(book);

        try (MockedStatic<BookRequestDTO> mockedStaticRequest = mockStatic(BookRequestDTO.class);
             MockedStatic<BookResponseDTO> mockedStaticResponse = mockStatic(BookResponseDTO.class)) {

            mockedStaticRequest.when(() -> BookRequestDTO.toBook(any(BookRequestDTO.class))).thenReturn(book);
            mockedStaticResponse.when(() -> BookResponseDTO.fromBook(any(Book.class))).thenReturn(bookResponseDTO);

            // Act
            ResponseEntity<BookResponseDTO> response = bookController.updateBook(bookId, bookRequestDTO);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(bookId, response.getBody().getId());
            assertEquals("Test Book", response.getBody().getTitle());

            verify(bookService, times(1)).updateBook(eq(bookId), any(Book.class));
            mockedStaticRequest.verify(() -> BookRequestDTO.toBook(any(BookRequestDTO.class)), times(1));
            mockedStaticResponse.verify(() -> BookResponseDTO.fromBook(any(Book.class)), times(1));
        }
    }

    @Test
    void deleteBook_WhenBookExists_ReturnsNoContentResponse() {
        // Arrange
        doNothing().when(bookService).deleteBook(bookId);

        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }
}
