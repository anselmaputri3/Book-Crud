package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

public class GetBookByIdUseCase {
    private final BookRepository repository;

    public GetBookByIdUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public Book execute(int id) {
        return repository.getBookById(id);
    }
}
