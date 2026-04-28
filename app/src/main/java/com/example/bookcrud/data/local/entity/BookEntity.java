package com.example.bookcrud.data.local.entity;

public class BookEntity {
    private int id;
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String genre;
    private String synopsis;
    private String coverPath;
    private int pages;
    private float rating;
    private boolean isReading;
    private int readingProgress;

    public BookEntity() {
    }

    public BookEntity(int id, String title, String author, int year, String isbn,
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
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public String getCoverPath() { return coverPath; }
    public void setCoverPath(String coverPath) { this.coverPath = coverPath; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public boolean isReading() { return isReading; }
    public void setReading(boolean reading) { isReading = reading; }
    public int getReadingProgress() { return readingProgress; }
    public void setReadingProgress(int readingProgress) { this.readingProgress = readingProgress; }
}
