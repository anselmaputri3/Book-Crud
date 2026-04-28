package com.example.bookcrud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bookcrud.model.Book;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_ISBN = "isbn";

    private static final String CREATE_TABLE_BOOKS =
            "CREATE TABLE " + TABLE_BOOKS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT NOT NULL, "
                    + COLUMN_AUTHOR + " TEXT NOT NULL, "
                    + COLUMN_YEAR + " INTEGER, "
                    + COLUMN_ISBN + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    // Create
    public long insertBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_YEAR, book.getYear());
        values.put(COLUMN_ISBN, book.getIsbn());

        long id = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return id;
    }

    // Read - get single book
    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_YEAR, COLUMN_ISBN},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Book book = null;
        if (cursor != null && cursor.moveToFirst()) {
            book = new Book(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))
            );
            cursor.close();
        }
        db.close();
        return book;
    }

    // Read - get all books
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS + " ORDER BY " + COLUMN_TITLE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    // Update
    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_YEAR, book.getYear());
        values.put(COLUMN_ISBN, book.getIsbn());

        int rowsAffected = db.update(TABLE_BOOKS, values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(book.getId())});
        db.close();
        return rowsAffected;
    }

    // Delete
    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Search books by title or author
    public List<Book> searchBooks(String keyword) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKS
                + " WHERE " + COLUMN_TITLE + " LIKE ? OR " + COLUMN_AUTHOR + " LIKE ?"
                + " ORDER BY " + COLUMN_TITLE + " ASC";
        String searchPattern = "%" + keyword + "%";
        Cursor cursor = db.rawQuery(query, new String[]{searchPattern, searchPattern});

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    // Get total book count
    public int getBookCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKS, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}
