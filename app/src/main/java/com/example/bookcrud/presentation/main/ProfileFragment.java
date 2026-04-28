package com.example.bookcrud.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookcrud.R;
import com.example.bookcrud.di.Injection;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.presentation.booklist.BookListViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private BookListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this,
                Injection.provideBookListViewModelFactory(requireContext()))
                .get(BookListViewModel.class);

        TextView tvTotalBooks = view.findViewById(R.id.tvTotalBooks);
        TextView tvGoalProgress = view.findViewById(R.id.tvGoalProgress);
        ProgressBar progressGoal = view.findViewById(R.id.progressGoal);
        LinearLayout llGenreStats = view.findViewById(R.id.llGenreStats);
        TextView tvNoGenreData = view.findViewById(R.id.tvNoGenreData);

        viewModel.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            int count = books.size();
            tvTotalBooks.setText(String.valueOf(count));
            tvGoalProgress.setText(String.valueOf(count));
            progressGoal.setProgress(Math.min(count, 100));

            updateGenreStats(llGenreStats, tvNoGenreData, books);
        });

        viewModel.loadBooks();
    }

    private void updateGenreStats(LinearLayout container, TextView noData, List<Book> books) {
        container.removeAllViews();
        Map<String, Integer> genreCount = new HashMap<>();
        for (Book book : books) {
            String genre = book.getGenre();
            if (genre != null && !genre.isEmpty()) {
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }

        if (genreCount.isEmpty()) {
            noData.setVisibility(View.VISIBLE);
            return;
        }

        noData.setVisibility(View.GONE);
        int total = books.size();
        int[] colors = {
                getResources().getColor(R.color.purple_primary, null),
                getResources().getColor(R.color.tag_scifi, null),
                getResources().getColor(R.color.green_accent, null),
                getResources().getColor(R.color.tag_fiction, null),
                getResources().getColor(R.color.star_active, null),
        };
        int colorIdx = 0;

        for (Map.Entry<String, Integer> entry : genreCount.entrySet()) {
            View row = LayoutInflater.from(requireContext()).inflate(R.layout.item_genre_stat, container, false);
            TextView tvName = row.findViewById(R.id.tvGenreName);
            TextView tvPercent = row.findViewById(R.id.tvGenrePercent);
            ProgressBar bar = row.findViewById(R.id.progressGenre);

            int percent = (int) ((entry.getValue() / (float) total) * 100);
            tvName.setText(entry.getKey());
            tvPercent.setText(percent + "%");
            bar.setProgress(percent);
            bar.getProgressDrawable().setTint(colors[colorIdx % colors.length]);
            colorIdx++;

            container.addView(row);
        }
    }
}
