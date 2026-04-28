package com.example.bookcrud.presentation.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookcrud.R;
import com.example.bookcrud.domain.entity.Book;

import java.io.File;
import java.util.List;

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.BookViewHolder> {

    private List<Book> bookList;
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onBookLongClick(Book book);
    }

    public BookGridAdapter(List<Book> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_grid, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book, listener);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateData(List<Book> newList) {
        this.bookList = newList;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivBookCover;
        private final TextView tvTitle;
        private final TextView tvAuthor;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }

        void bind(Book book, OnBookClickListener listener) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());

            String coverPath = book.getCoverPath();
            if (coverPath != null && !coverPath.isEmpty() && new File(coverPath).exists()) {
                ivBookCover.setImageBitmap(BitmapFactory.decodeFile(coverPath));
                ivBookCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                ivBookCover.setImageResource(android.R.drawable.ic_menu_gallery);
                ivBookCover.setScaleType(ImageView.ScaleType.CENTER);
            }

            itemView.setOnClickListener(v -> listener.onBookClick(book));
            itemView.setOnLongClickListener(v -> {
                listener.onBookLongClick(book);
                return true;
            });
        }
    }
}
