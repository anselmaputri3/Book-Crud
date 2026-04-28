package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

import java.util.List;

public class SearchBooksUseCase {
    private final BookRepository repository;

    public SearchBooksUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> execute(String keyword) {
        return repository.searchBooks(keyword);
    }
}
