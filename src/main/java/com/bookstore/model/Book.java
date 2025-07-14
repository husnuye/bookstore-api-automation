package com.bookstore.model;

/**
 * Data model class for Book entity.
 * Represents the structure of book resource in the API.
 * Follow POJO (Plain Old Java Object) standard for easy JSON mapping.
 */
public class Book {
    // Unique identifier for each book
    private int id;
    // Title of the book
    private String title;
    // Short description or summary of the book
    private String description;
    // Number of pages in the book
    private int pageCount;
    // Excerpt or sample text of the book
    private String excerpt;
    // Publication date (ISO format recommended, e.g. "2023-05-01T00:00:00Z")
    private String publishDate;

    // Default constructor (required for Jackson and other mappers)
    public Book() { }

    // All-args constructor for easier object creation in tests
    public Book(int id, String title, String description, int pageCount, String excerpt, String publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.excerpt = excerpt;
        this.publishDate = publishDate;
    }

    // -- Getters and Setters --
    // Always generate all for compatibility with frameworks and tools

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    // toString, equals, hashCode can be generated if needed
}