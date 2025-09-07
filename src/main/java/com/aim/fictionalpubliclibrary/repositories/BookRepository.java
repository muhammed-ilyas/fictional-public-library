package com.aim.fictionalpubliclibrary.repositories;

import com.aim.fictionalpubliclibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * BookRepository interface for CRUD operations on Book entities.
 * Extends JpaRepository to provide standard database operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a book by its ID.
     * @param id Long
     * @return Optional<Book>
     */
    @Override
    Optional<Book> findById(Long id);
}
