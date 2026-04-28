package com.example.bookcrud.presentation.booklist;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetAllBooksUseCase;
import com.example.bookcrud.domain.usecase.SearchBooksUseCase;

import java.util.List;

public class BookListViewModel {

    private final GetAllBooksUseCase getAllBooksUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final SearchBooksUseCase searchBooksUseCase;

    public BookListViewModel(
            GetAllBooksUseCase getAllBooksUseCase,
            DeleteBookUseCase deleteBookUseCase,
            SearchBooksUseCase searchBooksUseCase
    ) {
        this.getAllBooksUseCase = getAllBooksUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
    }

    public List<Book> getAllBooks() {
        return getAllBooksUseCase.execute();
    }

    public void deleteBook(int id) {
        deleteBookUseCase.execute(id);
    }

    public List<Book> searchBooks(String keyword) {
        return searchBooksUseCase.execute(keyword);
    }
}
