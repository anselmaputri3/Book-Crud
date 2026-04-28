package com.example.bookcrud.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_ISBN = "isbn";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_SYNOPSIS = "synopsis";
    public static final String COLUMN_COVER_PATH = "cover_path";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_IS_READING = "is_reading";
    public static final String COLUMN_READING_PROGRESS = "reading_progress";

    private static final String CREATE_TABLE_BOOKS =
            "CREATE TABLE " + TABLE_BOOKS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT NOT NULL, "
                    + COLUMN_AUTHOR + " TEXT NOT NULL, "
                    + COLUMN_YEAR + " INTEGER, "
                    + COLUMN_ISBN + " TEXT, "
                    + COLUMN_GENRE + " TEXT, "
                    + COLUMN_SYNOPSIS + " TEXT, "
                    + COLUMN_COVER_PATH + " TEXT, "
                    + COLUMN_PAGES + " INTEGER DEFAULT 0, "
                    + COLUMN_RATING + " REAL DEFAULT 0, "
                    + COLUMN_IS_READING + " INTEGER DEFAULT 0, "
                    + COLUMN_READING_PROGRESS + " INTEGER DEFAULT 0"
                    + ")";

    private static BookDatabaseHelper instance;

    private BookDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized BookDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BookDatabaseHelper(context.getApplicationContext());
        }
        return instance;
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
}
