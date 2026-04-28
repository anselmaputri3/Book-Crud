package com.example.bookcrud.presentation.booklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.presentation.adapter.BookAdapter;
import com.example.bookcrud.presentation.addeditbook.AddEditBookActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookListViewModel viewModel;
    private List<Book> bookList;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new BookListViewModel(
                Injection.provideGetAllBooksUseCase(this),
                Injection.provideDeleteBookUseCase(this),
                Injection.provideSearchBooksUseCase(this)
        );

        bookList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        SearchView searchView = findViewById(R.id.searchView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(bookAdapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, AddEditBookActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadBooks();
                } else {
                    performSearch(newText);
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
        bookList = viewModel.getAllBooks();
        bookAdapter.updateData(bookList);
        updateEmptyView();
    }

    private void performSearch(String keyword) {
        bookList = viewModel.searchBooks(keyword);
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
                    viewModel.deleteBook(book.getId());
                    loadBooks();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
