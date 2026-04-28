package com.example.bookcrud.domain.repository;

import com.example.bookcrud.domain.entity.Book;

import java.util.List;

public interface BookRepository {
    long insertBook(Book book);
    Book getBookById(int id);
    List<Book> getAllBooks();
    int updateBook(Book book);
    void deleteBook(int id);
    List<Book> searchBooks(String keyword);
    int getBookCount();
}
