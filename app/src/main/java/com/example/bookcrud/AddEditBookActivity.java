package com.example.bookcrud;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookcrud.database.DatabaseHelper;
import com.example.bookcrud.model.Book;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditBookActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etAuthor, etYear, etIsbn;
    private DatabaseHelper databaseHelper;
    private boolean isEditMode = false;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        databaseHelper = new DatabaseHelper(this);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etYear = findViewById(R.id.etYear);
        etIsbn = findViewById(R.id.etIsbn);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (getIntent().hasExtra("book_id")) {
            isEditMode = true;
            bookId = getIntent().getIntExtra("book_id", -1);
            etTitle.setText(getIntent().getStringExtra("book_title"));
            etAuthor.setText(getIntent().getStringExtra("book_author"));
            etYear.setText(String.valueOf(getIntent().getIntExtra("book_year", 0)));
            etIsbn.setText(getIntent().getStringExtra("book_isbn"));

            setTitle("Edit Buku");
            btnSave.setText("Update");
        } else {
            setTitle("Tambah Buku");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(v -> saveBook());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveBook() {
        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String author = etAuthor.getText() != null ? etAuthor.getText().toString().trim() : "";
        String yearStr = etYear.getText() != null ? etYear.getText().toString().trim() : "";
        String isbn = etIsbn.getText() != null ? etIsbn.getText().toString().trim() : "";

        if (title.isEmpty()) {
            etTitle.setError("Judul buku harus diisi");
            etTitle.requestFocus();
            return;
        }

        if (author.isEmpty()) {
            etAuthor.setError("Penulis harus diisi");
            etAuthor.requestFocus();
            return;
        }

        int year = 0;
        if (!yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                etYear.setError("Tahun tidak valid");
                etYear.requestFocus();
                return;
            }
        }

        Book book = new Book(title, author, year, isbn);

        if (isEditMode) {
            book.setId(bookId);
            databaseHelper.updateBook(book);
            Toast.makeText(this, "Buku berhasil diupdate", Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper.insertBook(book);
            Toast.makeText(this, "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
