package com.example.bookcrud.presentation.addeditbook;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.usecase.GetBookByIdUseCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddEditBookActivity extends AppCompatActivity {

    private AddEditBookViewModel viewModel;
    private EditText etTitle, etAuthor, etYear, etIsbn, etPages, etSynopsis;
    private Spinner spinnerGenre;
    private ImageView ivCoverPreview;
    private boolean isEditMode = false;
    private int bookId = -1;
    private String coverPath = "";
    private Book existingBook;

    private final String[] genres = {
            "Select a genre…", "Fantasy", "Sci-Fi", "Fiction", "Non-Fiction",
            "Mystery", "Romance", "Thriller", "Horror", "Biography", "History", "Classic"
    };

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        saveCoverImage(uri);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        viewModel = new ViewModelProvider(this,
                Injection.provideAddEditBookViewModelFactory(this))
                .get(AddEditBookViewModel.class);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etYear = findViewById(R.id.etYear);
        etIsbn = findViewById(R.id.etIsbn);
        etPages = findViewById(R.id.etPages);
        etSynopsis = findViewById(R.id.etSynopsis);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        ivCoverPreview = findViewById(R.id.ivCoverPreview);
        TextView tvFormTitle = findViewById(R.id.tvFormTitle);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genres);
        spinnerGenre.setAdapter(genreAdapter);

        if (getIntent().hasExtra("book_id")) {
            isEditMode = true;
            bookId = getIntent().getIntExtra("book_id", -1);
            tvFormTitle.setText("Edit Book");
            ((TextView) findViewById(R.id.btnSave)).setText("Update");
            loadBookData();
        }

        viewModel.getSaveSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this,
                        isEditMode ? "Buku berhasil diupdate" : "Buku berhasil ditambahkan",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getValidationError().observe(this, error -> {
            if ("title".equals(error)) {
                etTitle.setError("Judul buku harus diisi");
                etTitle.requestFocus();
            } else if ("author".equals(error)) {
                etAuthor.setError("Penulis harus diisi");
                etAuthor.requestFocus();
            }
        });

        findViewById(R.id.layoutUploadCover).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        findViewById(R.id.btnSave).setOnClickListener(v -> saveBook());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadBookData() {
        GetBookByIdUseCase useCase = Injection.provideGetBookByIdUseCase(this);
        existingBook = useCase.execute(bookId);
        if (existingBook != null) {
            etTitle.setText(existingBook.getTitle());
            etAuthor.setText(existingBook.getAuthor());
            etYear.setText(existingBook.getYear() > 0 ? String.valueOf(existingBook.getYear()) : "");
            etIsbn.setText(existingBook.getIsbn());
            etPages.setText(existingBook.getPages() > 0 ? String.valueOf(existingBook.getPages()) : "");
            etSynopsis.setText(existingBook.getSynopsis());
            coverPath = existingBook.getCoverPath() != null ? existingBook.getCoverPath() : "";

            if (!coverPath.isEmpty() && new File(coverPath).exists()) {
                ivCoverPreview.setImageBitmap(BitmapFactory.decodeFile(coverPath));
                ivCoverPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            String genre = existingBook.getGenre();
            if (genre != null) {
                for (int i = 0; i < genres.length; i++) {
                    if (genres[i].equalsIgnoreCase(genre)) {
                        spinnerGenre.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String isbn = etIsbn.getText().toString().trim();
        String pagesStr = etPages.getText().toString().trim();
        String synopsis = etSynopsis.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItemPosition() > 0
                ? spinnerGenre.getSelectedItem().toString() : "";

        int year = 0;
        if (!yearStr.isEmpty()) {
            try { year = Integer.parseInt(yearStr); }
            catch (NumberFormatException ignored) {}
        }

        int pages = 0;
        if (!pagesStr.isEmpty()) {
            try { pages = Integer.parseInt(pagesStr); }
            catch (NumberFormatException ignored) {}
        }

        float rating = existingBook != null ? existingBook.getRating() : 0;
        boolean isReading = existingBook != null && existingBook.isReading();
        int readingProgress = existingBook != null ? existingBook.getReadingProgress() : 0;

        viewModel.saveBook(bookId, isEditMode, title, author, year, isbn,
                genre, synopsis, coverPath, pages, rating, isReading, readingProgress);
    }

    private void saveCoverImage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) return;

            File coverDir = new File(getFilesDir(), "covers");
            if (!coverDir.exists()) coverDir.mkdirs();

            File coverFile = new File(coverDir, "cover_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(coverFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            inputStream.close();

            coverPath = coverFile.getAbsolutePath();
            ivCoverPreview.setImageBitmap(BitmapFactory.decodeFile(coverPath));
            ivCoverPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }
}
