package com.example.bookcrud.presentation.addeditbook;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.domain.usecase.InsertBookUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class AddEditBookViewModelFactory implements ViewModelProvider.Factory {

    private final InsertBookUseCase insertBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;

    public AddEditBookViewModelFactory(InsertBookUseCase insertBookUseCase, UpdateBookUseCase updateBookUseCase) {
        this.insertBookUseCase = insertBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddEditBookViewModel(insertBookUseCase, updateBookUseCase);
    }
}
