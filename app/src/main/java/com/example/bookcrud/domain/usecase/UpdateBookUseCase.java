package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

public class UpdateBookUseCase {
    private final BookRepository repository;

    public UpdateBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public int execute(Book book) {
        return repository.updateBook(book);
    }
}
