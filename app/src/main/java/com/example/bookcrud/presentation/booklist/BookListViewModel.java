package com.example.bookcrud.presentation.booklist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetAllBooksUseCase;
import com.example.bookcrud.domain.usecase.GetCurrentlyReadingUseCase;
import com.example.bookcrud.domain.usecase.SearchBooksUseCase;

import java.util.List;

public class BookListViewModel extends ViewModel {

    private final GetAllBooksUseCase getAllBooksUseCase;
    private final GetCurrentlyReadingUseCase getCurrentlyReadingUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final SearchBooksUseCase searchBooksUseCase;

    private final MutableLiveData<List<Book>> allBooks = new MutableLiveData<>();
    private final MutableLiveData<List<Book>> currentlyReading = new MutableLiveData<>();
    private final MutableLiveData<Integer> bookCount = new MutableLiveData<>();

    public BookListViewModel(
            GetAllBooksUseCase getAllBooksUseCase,
            GetCurrentlyReadingUseCase getCurrentlyReadingUseCase,
            DeleteBookUseCase deleteBookUseCase,
            SearchBooksUseCase searchBooksUseCase
    ) {
        this.getAllBooksUseCase = getAllBooksUseCase;
        this.getCurrentlyReadingUseCase = getCurrentlyReadingUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
    }

    public LiveData<List<Book>> getAllBooks() { return allBooks; }
    public LiveData<List<Book>> getCurrentlyReading() { return currentlyReading; }
    public LiveData<Integer> getBookCount() { return bookCount; }

    public void loadBooks() {
        List<Book> books = getAllBooksUseCase.execute();
        allBooks.setValue(books);
        bookCount.setValue(books.size());
    }

    public void loadCurrentlyReading() {
        currentlyReading.setValue(getCurrentlyReadingUseCase.execute());
    }

    public void deleteBook(int id) {
        deleteBookUseCase.execute(id);
        loadBooks();
        loadCurrentlyReading();
    }

    public void searchBooks(String keyword) {
        allBooks.setValue(searchBooksUseCase.execute(keyword));
    }
}
