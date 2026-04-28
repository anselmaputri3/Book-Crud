package com.example.bookcrud.presentation.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetBookByIdUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class BookDetailViewModelFactory implements ViewModelProvider.Factory {

    private final GetBookByIdUseCase getBookByIdUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    public BookDetailViewModelFactory(GetBookByIdUseCase getBookByIdUseCase,
                                       UpdateBookUseCase updateBookUseCase,
                                       DeleteBookUseCase deleteBookUseCase) {
        this.getBookByIdUseCase = getBookByIdUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookDetailViewModel(getBookByIdUseCase, updateBookUseCase, deleteBookUseCase);
    }
}
