package com.example.bookcrud.data.repository;

import com.example.bookcrud.data.local.dao.BookDao;
import com.example.bookcrud.data.local.entity.BookEntity;
import com.example.bookcrud.data.local.mapper.BookMapper;
import com.example.bookcrud.domain.entity.Book;
import com.example.bookcrud.domain.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;

    public BookRepositoryImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public long insertBook(Book book) {
        return bookDao.insert(BookMapper.toEntity(book));
    }

    @Override
    public Book getBookById(int id) {
        BookEntity entity = bookDao.getById(id);
        return entity != null ? BookMapper.toDomain(entity) : null;
    }

    @Override
    public List<Book> getAllBooks() {
        return mapList(bookDao.getAll());
    }

    @Override
    public List<Book> getCurrentlyReading() {
        return mapList(bookDao.getCurrentlyReading());
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.update(BookMapper.toEntity(book));
    }

    @Override
    public void deleteBook(int id) {
        bookDao.delete(id);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return mapList(bookDao.search(keyword));
    }

    @Override
    public int getBookCount() {
        return bookDao.count();
    }

    private List<Book> mapList(List<BookEntity> entities) {
        List<Book> books = new ArrayList<>();
        for (BookEntity entity : entities) {
            books.add(BookMapper.toDomain(entity));
        }
        return books;
    }
}
