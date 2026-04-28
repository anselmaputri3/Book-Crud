package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.repository.BookRepository;

public class DeleteBookUseCase {
    private final BookRepository repository;

    public DeleteBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public void execute(int id) {
        repository.deleteBook(id);
    }
}
