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
        BookEntity entity = BookMapper.toEntity(book);
        return bookDao.insert(entity);
    }

    @Override
    public Book getBookById(int id) {
        BookEntity entity = bookDao.getById(id);
        if (entity == null) return null;
        return BookMapper.toDomain(entity);
    }

    @Override
    public List<Book> getAllBooks() {
        List<BookEntity> entities = bookDao.getAll();
        List<Book> books = new ArrayList<>();
        for (BookEntity entity : entities) {
            books.add(BookMapper.toDomain(entity));
        }
        return books;
    }

    @Override
    public int updateBook(Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        return bookDao.update(entity);
    }

    @Override
    public void deleteBook(int id) {
        bookDao.delete(id);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        List<BookEntity> entities = bookDao.search(keyword);
        List<Book> books = new ArrayList<>();
        for (BookEntity entity : entities) {
            books.add(BookMapper.toDomain(entity));
        }
        return books;
    }

    @Override
    public int getBookCount() {
        return bookDao.count();
    }
}
