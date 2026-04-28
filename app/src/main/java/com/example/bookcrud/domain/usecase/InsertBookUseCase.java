package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

public class InsertBookUseCase {
    private final BookRepository repository;

    public InsertBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public long execute(Book book) {
        return repository.insertBook(book);
    }
}
