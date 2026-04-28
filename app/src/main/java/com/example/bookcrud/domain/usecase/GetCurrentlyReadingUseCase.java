package com.example.bookcrud.domain.usecase;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

import java.util.List;

public class GetCurrentlyReadingUseCase {
    private final BookRepository repository;

    public GetCurrentlyReadingUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> execute() {
        return repository.getCurrentlyReading();
    }
}
