package com.example.bookcrud.presentation.addeditbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.InsertBookUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class AddEditBookViewModel extends ViewModel {

    private final InsertBookUseCase insertBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;

    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> validationError = new MutableLiveData<>();

    public AddEditBookViewModel(InsertBookUseCase insertBookUseCase, UpdateBookUseCase updateBookUseCase) {
        this.insertBookUseCase = insertBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
    }

    public LiveData<Boolean> getSaveSuccess() { return saveSuccess; }
    public LiveData<String> getValidationError() { return validationError; }

    public void saveBook(int id, boolean isEdit, String title, String author, int year,
                         String isbn, String genre, String synopsis, String coverPath,
                         int pages, float rating, boolean isReading, int readingProgress) {
        if (title == null || title.trim().isEmpty()) {
            validationError.setValue("title");
            return;
        }
        if (author == null || author.trim().isEmpty()) {
            validationError.setValue("author");
            return;
        }

        Book book = new Book(id, title.trim(), author.trim(), year, isbn,
                genre, synopsis, coverPath, pages, rating, isReading, readingProgress);

        if (isEdit) {
            updateBookUseCase.execute(book);
        } else {
            insertBookUseCase.execute(book);
        }
        saveSuccess.setValue(true);
    }
}
