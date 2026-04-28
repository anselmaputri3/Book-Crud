package com.example.bookcrud.presentation.detail;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.presentation.addeditbook.AddEditBookActivity;

import java.io.File;

public class BookDetailActivity extends AppCompatActivity {

    private BookDetailViewModel viewModel;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        viewModel = new ViewModelProvider(this,
                Injection.provideBookDetailViewModelFactory(this))
                .get(BookDetailViewModel.class);

        bookId = getIntent().getIntExtra("book_id", -1);
        if (bookId == -1) {
            finish();
            return;
        }

        ImageView ivCover = findViewById(R.id.ivDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvGenre = findViewById(R.id.tvDetailGenre);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvRating = findViewById(R.id.tvRatingValue);
        TextView tvSynopsis = findViewById(R.id.tvDetailSynopsis);
        TextView tvYear = findViewById(R.id.tvDetailYear);
        TextView tvPages = findViewById(R.id.tvDetailPages);
        TextView tvIsbn = findViewById(R.id.tvDetailIsbn);
        TextView btnMarkAsRead = findViewById(R.id.btnMarkAsRead);

        viewModel.getBook().observe(this, book -> {
            if (book == null) return;

            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvGenre.setText(book.getGenre() != null && !book.getGenre().isEmpty() ? book.getGenre() : "Fiction");
            ratingBar.setRating(book.getRating());
            tvRating.setText(String.format("%.1f", book.getRating()));
            tvSynopsis.setText(book.getSynopsis() != null ? book.getSynopsis() : "No synopsis available.");
            tvYear.setText(book.getYear() > 0 ? String.valueOf(book.getYear()) : "-");
            tvPages.setText(book.getPages() > 0 ? String.valueOf(book.getPages()) : "-");
            tvIsbn.setText(book.getIsbn() != null && !book.getIsbn().isEmpty() ? book.getIsbn() : "-");

            if (book.isReading()) {
                btnMarkAsRead.setText("Mark as Read");
                btnMarkAsRead.setBackgroundResource(R.drawable.bg_button_green);
            } else {
                btnMarkAsRead.setText("Start Reading");
                btnMarkAsRead.setBackgroundResource(R.drawable.bg_button_purple);
            }

            String coverPath = book.getCoverPath();
            if (coverPath != null && !coverPath.isEmpty() && new File(coverPath).exists()) {
                ivCover.setImageBitmap(BitmapFactory.decodeFile(coverPath));
            }
        });

        viewModel.getDeleteSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this, "Buku berhasil dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnMarkAsRead).setOnClickListener(v -> {
            Book currentBook = viewModel.getBook().getValue();
            if (currentBook != null) {
                if (currentBook.isReading()) {
                    viewModel.markAsRead(bookId);
                    Toast.makeText(this, "Marked as read!", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.toggleReading();
                    Toast.makeText(this, "Started reading!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnEditBook).setOnClickListener(v -> {
            Book book = viewModel.getBook().getValue();
            if (book != null) {
                Intent intent = new Intent(this, AddEditBookActivity.class);
                intent.putExtra("book_id", book.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            Book book = viewModel.getBook().getValue();
            if (book != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Hapus Buku")
                        .setMessage("Apakah Anda yakin ingin menghapus \"" + book.getTitle() + "\"?")
                        .setPositiveButton("Hapus", (dialog, which) -> viewModel.deleteBook(bookId))
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });

        viewModel.loadBook(bookId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadBook(bookId);
    }
}
