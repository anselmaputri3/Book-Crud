package com.example.bookcrud.presentation.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetBookByIdUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class BookDetailViewModel extends ViewModel {

    private final GetBookByIdUseCase getBookByIdUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    private final MutableLiveData<Book> book = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteSuccess = new MutableLiveData<>();

    public BookDetailViewModel(GetBookByIdUseCase getBookByIdUseCase,
                                UpdateBookUseCase updateBookUseCase,
                                DeleteBookUseCase deleteBookUseCase) {
        this.getBookByIdUseCase = getBookByIdUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
    }

    public LiveData<Book> getBook() { return book; }
    public LiveData<Boolean> getDeleteSuccess() { return deleteSuccess; }

    public void loadBook(int id) {
        book.setValue(getBookByIdUseCase.execute(id));
    }

    public void toggleReading() {
        Book currentBook = book.getValue();
        if (currentBook != null) {
            boolean nowReading = !currentBook.isReading();
            int progress = nowReading ? 0 : 100;
            Book updated = new Book(
                    currentBook.getId(), currentBook.getTitle(), currentBook.getAuthor(),
                    currentBook.getYear(), currentBook.getIsbn(), currentBook.getGenre(),
                    currentBook.getSynopsis(), currentBook.getCoverPath(), currentBook.getPages(),
                    currentBook.getRating(), nowReading, progress
            );
            updateBookUseCase.execute(updated);
            book.setValue(updated);
        }
    }

    public void markAsRead(int bookId) {
        Book currentBook = book.getValue();
        if (currentBook != null) {
            Book updated = new Book(
                    currentBook.getId(), currentBook.getTitle(), currentBook.getAuthor(),
                    currentBook.getYear(), currentBook.getIsbn(), currentBook.getGenre(),
                    currentBook.getSynopsis(), currentBook.getCoverPath(), currentBook.getPages(),
                    currentBook.getRating(), false, 100
            );
            updateBookUseCase.execute(updated);
            book.setValue(updated);
        }
    }

    public void deleteBook(int id) {
        deleteBookUseCase.execute(id);
        deleteSuccess.setValue(true);
    }
}
