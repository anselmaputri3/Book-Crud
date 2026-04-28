package com.example.bookcrud.domain.entity;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int year;
    private final String isbn;
    private final String genre;
    private final String synopsis;
    private final String coverPath;
    private final int pages;
    private final float rating;
    private final boolean isReading;
    private final int readingProgress;

    public Book(int id, String title, String author, int year, String isbn,
                String genre, String synopsis, String coverPath, int pages,
                float rating, boolean isReading, int readingProgress) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.genre = genre;
        this.synopsis = synopsis;
        this.coverPath = coverPath;
        this.pages = pages;
        this.rating = rating;
        this.isReading = isReading;
        this.readingProgress = readingProgress;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public String getSynopsis() { return synopsis; }
    public String getCoverPath() { return coverPath; }
    public int getPages() { return pages; }
    public float getRating() { return rating; }
    public boolean isReading() { return isReading; }
    public int getReadingProgress() { return readingProgress; }
}
