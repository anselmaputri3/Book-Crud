package com.example.bookcrud.data.local.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookcrud.data.local.database.BookDatabaseHelper;
import com.example.bookcrud.data.local.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final BookDatabaseHelper dbHelper;

    public BookDao(BookDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(BookEntity entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = toContentValues(entity);
        long id = db.insert(BookDatabaseHelper.TABLE_BOOKS, null, values);
        db.close();
        return id;
    }

    public BookEntity getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                BookDatabaseHelper.TABLE_BOOKS,
                null,
                BookDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        BookEntity entity = null;
        if (cursor != null && cursor.moveToFirst()) {
            entity = cursorToEntity(cursor);
            cursor.close();
        }
        db.close();
        return entity;
    }

    public List<BookEntity> getAll() {
        List<BookEntity> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                BookDatabaseHelper.TABLE_BOOKS,
                null, null, null, null, null,
                BookDatabaseHelper.COLUMN_TITLE + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int update(BookEntity entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = toContentValues(entity);
        int rows = db.update(
                BookDatabaseHelper.TABLE_BOOKS,
                values,
                BookDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(entity.getId())}
        );
        db.close();
        return rows;
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                BookDatabaseHelper.TABLE_BOOKS,
                BookDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );
        db.close();
    }

    public List<BookEntity> search(String keyword) {
        List<BookEntity> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + BookDatabaseHelper.TABLE_BOOKS
                + " WHERE " + BookDatabaseHelper.COLUMN_TITLE + " LIKE ?"
                + " OR " + BookDatabaseHelper.COLUMN_AUTHOR + " LIKE ?"
                + " ORDER BY " + BookDatabaseHelper.COLUMN_TITLE + " ASC";
        String pattern = "%" + keyword + "%";
        Cursor cursor = db.rawQuery(query, new String[]{pattern, pattern});

        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int count() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + BookDatabaseHelper.TABLE_BOOKS, null
        );
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    private ContentValues toContentValues(BookEntity entity) {
        ContentValues values = new ContentValues();
        values.put(BookDatabaseHelper.COLUMN_TITLE, entity.getTitle());
        values.put(BookDatabaseHelper.COLUMN_AUTHOR, entity.getAuthor());
        values.put(BookDatabaseHelper.COLUMN_YEAR, entity.getYear());
        values.put(BookDatabaseHelper.COLUMN_ISBN, entity.getIsbn());
        return values;
    }

    private BookEntity cursorToEntity(Cursor cursor) {
        return new BookEntity(
                cursor.getInt(cursor.getColumnIndexOrThrow(BookDatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(BookDatabaseHelper.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(BookDatabaseHelper.COLUMN_AUTHOR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(BookDatabaseHelper.COLUMN_YEAR)),
                cursor.getString(cursor.getColumnIndexOrThrow(BookDatabaseHelper.COLUMN_ISBN))
        );
    }
}
