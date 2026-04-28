package com.example.bookcrud.presentation.addeditbook;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.InsertBookUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class AddEditBookViewModel {

    private final InsertBookUseCase insertBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;

    public AddEditBookViewModel(
            InsertBookUseCase insertBookUseCase,
            UpdateBookUseCase updateBookUseCase
    ) {
        this.insertBookUseCase = insertBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
    }

    public long insertBook(String title, String author, int year, String isbn) {
        Book book = new Book(0, title, author, year, isbn);
        return insertBookUseCase.execute(book);
    }

    public int updateBook(int id, String title, String author, int year, String isbn) {
        Book book = new Book(id, title, author, year, isbn);
        return updateBookUseCase.execute(book);
    }

    public String validateInput(String title, String author, String yearStr) {
        if (title == null || title.trim().isEmpty()) {
            return "title";
        }
        if (author == null || author.trim().isEmpty()) {
            return "author";
        }
        if (yearStr != null && !yearStr.trim().isEmpty()) {
            try {
                Integer.parseInt(yearStr.trim());
            } catch (NumberFormatException e) {
                return "year";
            }
        }
        return null;
    }
}
