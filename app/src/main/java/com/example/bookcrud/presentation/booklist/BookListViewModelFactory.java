package com.example.bookcrud.presentation.booklist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetAllBooksUseCase;
import com.example.bookcrud.domain.usecase.GetCurrentlyReadingUseCase;
import com.example.bookcrud.domain.usecase.SearchBooksUseCase;

public class BookListViewModelFactory implements ViewModelProvider.Factory {

    private final GetAllBooksUseCase getAllBooksUseCase;
    private final GetCurrentlyReadingUseCase getCurrentlyReadingUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final SearchBooksUseCase searchBooksUseCase;

    public BookListViewModelFactory(
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

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookListViewModel(
                getAllBooksUseCase, getCurrentlyReadingUseCase,
                deleteBookUseCase, searchBooksUseCase
        );
    }
}
