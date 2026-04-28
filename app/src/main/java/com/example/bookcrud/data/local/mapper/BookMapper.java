package com.example.bookcrud.data.local.mapper;

import com.example.bookcrud.data.local.entity.BookEntity;
import com.example.bookcrud.domain.entity.Book;

public class BookMapper {

    public static Book toDomain(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getYear(),
                entity.getIsbn(),
                entity.getGenre(),
                entity.getSynopsis(),
                entity.getCoverPath(),
                entity.getPages(),
                entity.getRating(),
                entity.isReading(),
                entity.getReadingProgress()
        );
    }

    public static BookEntity toEntity(Book domain) {
        return new BookEntity(
                domain.getId(),
                domain.getTitle(),
                domain.getAuthor(),
                domain.getYear(),
                domain.getIsbn(),
                domain.getGenre(),
                domain.getSynopsis(),
                domain.getCoverPath(),
                domain.getPages(),
                domain.getRating(),
                domain.isReading(),
                domain.getReadingProgress()
        );
    }
}
