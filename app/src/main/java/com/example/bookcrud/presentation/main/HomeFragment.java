package com.example.bookcrud.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.presentation.adapter.BookGridAdapter;
import com.example.bookcrud.presentation.booklist.BookListViewModel;
import com.example.bookcrud.presentation.detail.BookDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements BookGridAdapter.OnBookClickListener {

    private BookListViewModel viewModel;
    private BookGridAdapter adapter;
    private RecyclerView rvLibrary;
    private TextView tvEmptyLibrary;
    private CardView cardCurrentlyReading;
    private TextView tvNoCurrentlyReading;
    private TextView tvCurrentTitle, tvCurrentAuthor, tvCurrentSynopsis, tvCurrentGenre, tvCurrentProgress;
    private ProgressBar progressCurrent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this,
                Injection.provideBookListViewModelFactory(requireContext()))
                .get(BookListViewModel.class);

        rvLibrary = view.findViewById(R.id.rvLibrary);
        tvEmptyLibrary = view.findViewById(R.id.tvEmptyLibrary);
        cardCurrentlyReading = view.findViewById(R.id.cardCurrentlyReading);
        tvNoCurrentlyReading = view.findViewById(R.id.tvNoCurrentlyReading);
        tvCurrentTitle = view.findViewById(R.id.tvCurrentTitle);
        tvCurrentAuthor = view.findViewById(R.id.tvCurrentAuthor);
        tvCurrentSynopsis = view.findViewById(R.id.tvCurrentSynopsis);
        tvCurrentGenre = view.findViewById(R.id.tvCurrentGenre);
        tvCurrentProgress = view.findViewById(R.id.tvCurrentProgress);
        progressCurrent = view.findViewById(R.id.progressCurrent);
        EditText etSearch = view.findViewById(R.id.etSearch);

        adapter = new BookGridAdapter(new ArrayList<>(), this);
        rvLibrary.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvLibrary.setAdapter(adapter);

        viewModel.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            adapter.updateData(books);
            tvEmptyLibrary.setVisibility(books.isEmpty() ? View.VISIBLE : View.GONE);
            rvLibrary.setVisibility(books.isEmpty() ? View.GONE : View.VISIBLE);
        });

        viewModel.getCurrentlyReading().observe(getViewLifecycleOwner(), this::updateCurrentlyReading);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    viewModel.loadBooks();
                } else {
                    viewModel.searchBooks(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        view.findViewById(R.id.btnContinueReading).setOnClickListener(v -> {
            List<Book> reading = viewModel.getCurrentlyReading().getValue();
            if (reading != null && !reading.isEmpty()) {
                openBookDetail(reading.get(0));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadBooks();
        viewModel.loadCurrentlyReading();
    }

    private void updateCurrentlyReading(List<Book> books) {
        if (books == null || books.isEmpty()) {
            cardCurrentlyReading.setVisibility(View.GONE);
            tvNoCurrentlyReading.setVisibility(View.VISIBLE);
        } else {
            cardCurrentlyReading.setVisibility(View.VISIBLE);
            tvNoCurrentlyReading.setVisibility(View.GONE);
            Book book = books.get(0);
            tvCurrentTitle.setText(book.getTitle());
            tvCurrentAuthor.setText(book.getAuthor());
            tvCurrentSynopsis.setText(book.getSynopsis() != null ? book.getSynopsis() : "");
            tvCurrentGenre.setText(book.getGenre() != null ? book.getGenre() : "Fiction");
            progressCurrent.setProgress(book.getReadingProgress());
            tvCurrentProgress.setText(book.getReadingProgress() + "%");
        }
    }

    @Override
    public void onBookClick(Book book) {
        openBookDetail(book);
    }

    @Override
    public void onBookLongClick(Book book) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Hapus Buku")
                .setMessage("Apakah Anda yakin ingin menghapus \"" + book.getTitle() + "\"?")
                .setPositiveButton("Hapus", (dialog, which) -> viewModel.deleteBook(book.getId()))
                .setNegativeButton("Batal", null)
                .show();
    }

    private void openBookDetail(Book book) {
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book_id", book.getId());
        startActivity(intent);
    }
}
