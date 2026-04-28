package com.example.bookcrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcrud.adapter.BookAdapter;
import com.example.bookcrud.database.DatabaseHelper;
import com.example.bookcrud.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private DatabaseHelper databaseHelper;
    private List<Book> bookList;
    private TextView tvEmpty;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        bookList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        searchView = findViewById(R.id.searchView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(bookAdapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadBooks();
                } else {
                    searchBooks(newText);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        bookList = databaseHelper.getAllBooks();
        bookAdapter.updateData(bookList);
        updateEmptyView();
    }

    private void searchBooks(String keyword) {
        bookList = databaseHelper.searchBooks(keyword);
        bookAdapter.updateData(bookList);
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (bookList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEditClick(Book book) {
        Intent intent = new Intent(this, AddEditBookActivity.class);
        intent.putExtra("book_id", book.getId());
        intent.putExtra("book_title", book.getTitle());
        intent.putExtra("book_author", book.getAuthor());
        intent.putExtra("book_year", book.getYear());
        intent.putExtra("book_isbn", book.getIsbn());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Buku")
                .setMessage("Apakah Anda yakin ingin menghapus \"" + book.getTitle() + "\"?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    databaseHelper.deleteBook(book.getId());
                    loadBooks();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
