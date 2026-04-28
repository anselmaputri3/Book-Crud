package com.example.bookcrud.presentation.addeditbook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditBookActivity extends AppCompatActivity {

    private TextInputEditText etTitle;
    private TextInputEditText etAuthor;
    private TextInputEditText etYear;
    private TextInputEditText etIsbn;
    private AddEditBookViewModel viewModel;
    private boolean isEditMode = false;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        viewModel = new AddEditBookViewModel(
                Injection.provideInsertBookUseCase(this),
                Injection.provideUpdateBookUseCase(this)
        );

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
        String title = getText(etTitle);
        String author = getText(etAuthor);
        String yearStr = getText(etYear);
        String isbn = getText(etIsbn);

        String validationError = viewModel.validateInput(title, author, yearStr);
        if (validationError != null) {
            switch (validationError) {
                case "title":
                    etTitle.setError("Judul buku harus diisi");
                    etTitle.requestFocus();
                    break;
                case "author":
                    etAuthor.setError("Penulis harus diisi");
                    etAuthor.requestFocus();
                    break;
                case "year":
                    etYear.setError("Tahun tidak valid");
                    etYear.requestFocus();
                    break;
            }
            return;
        }

        int year = yearStr.isEmpty() ? 0 : Integer.parseInt(yearStr);

        if (isEditMode) {
            viewModel.updateBook(bookId, title, author, year, isbn);
            Toast.makeText(this, "Buku berhasil diupdate", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertBook(title, author, year, isbn);
            Toast.makeText(this, "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
