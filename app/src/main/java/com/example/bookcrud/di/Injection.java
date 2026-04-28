package com.example.bookcrud.di;

import android.content.Context;

import com.example.bookcrud.data.local.dao.BookDao;
import com.example.bookcrud.data.local.database.BookDatabaseHelper;
import com.example.bookcrud.data.repository.BookRepositoryImpl;
import com.example.bookcrud.domain.repository.BookRepository;
import com.example.bookcrud.domain.usecase.DeleteBookUseCase;
import com.example.bookcrud.domain.usecase.GetAllBooksUseCase;
import com.example.bookcrud.domain.usecase.GetBookByIdUseCase;
import com.example.bookcrud.domain.usecase.InsertBookUseCase;
import com.example.bookcrud.domain.usecase.SearchBooksUseCase;
import com.example.bookcrud.domain.usecase.UpdateBookUseCase;

public class Injection {

    private static BookRepository bookRepository;

    public static BookRepository provideBookRepository(Context context) {
        if (bookRepository == null) {
            BookDatabaseHelper dbHelper = BookDatabaseHelper.getInstance(context);
            BookDao bookDao = new BookDao(dbHelper);
            bookRepository = new BookRepositoryImpl(bookDao);
        }
        return bookRepository;
    }

    public static GetAllBooksUseCase provideGetAllBooksUseCase(Context context) {
        return new GetAllBooksUseCase(provideBookRepository(context));
    }

    public static GetBookByIdUseCase provideGetBookByIdUseCase(Context context) {
        return new GetBookByIdUseCase(provideBookRepository(context));
    }

    public static InsertBookUseCase provideInsertBookUseCase(Context context) {
        return new InsertBookUseCase(provideBookRepository(context));
    }

    public static UpdateBookUseCase provideUpdateBookUseCase(Context context) {
        return new UpdateBookUseCase(provideBookRepository(context));
    }

    public static DeleteBookUseCase provideDeleteBookUseCase(Context context) {
        return new DeleteBookUseCase(provideBookRepository(context));
    }

    public static SearchBooksUseCase provideSearchBooksUseCase(Context context) {
        return new SearchBooksUseCase(provideBookRepository(context));
    }
}
