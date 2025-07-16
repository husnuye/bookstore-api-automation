package com.bookstore.model;

/**
 * Represents a Book entity as defined in the Bookstore API.
 * <p>
 * This Plain Old Java Object (POJO) maps directly to the JSON structure
 * returned and accepted by the /Books endpoints.
 * Designed for use in serialization/deserialization and for building test data.
 * </p>
 * <p>
 * <b>Fields:</b>
 * <ul>
 *   <li><b>id</b> - Unique identifier for the book.</li>
 *   <li><b>title</b> - Book title.</li>
 *   <li><b>description</b> - Short description or summary.</li>
 *   <li><b>pageCount</b> - Total number of pages.</li>
 *   <li><b>excerpt</b> - Excerpt or sample text.</li>
 *   <li><b>publishDate</b> - Publication date in ISO-8601 format (e.g. "2023-05-01T00:00:00Z").</li>
 * </ul>
 * </p>
 *
 * <p>
 * Usage Example:<br>
 * <code>
 *   Book book = new Book(101, "QA Guide", "Complete test automation guide", 350, "Sample excerpt...", "2024-05-01T00:00:00Z");
 * </code>
 * </p>
 */
public class Book {

    /** Unique identifier for each book. */
    private int id;

    /** Title of the book. */
    private String title;

    /** Short description or summary of the book. */
    private String description;

    /** Total number of pages in the book. */
    private int pageCount;

    /** Excerpt or sample text from the book. */
    private String excerpt;

    /** Publication date in ISO-8601 format (e.g. "2023-05-01T00:00:00Z"). */
    private String publishDate;

    /**
     * Default no-args constructor required for deserialization.
     */
    public Book() { }

    /**
     * All-args constructor for easy creation of Book objects.
     *
     * @param id          Unique book identifier
     * @param title       Book title
     * @param description Description or summary
     * @param pageCount   Number of pages
     * @param excerpt     Excerpt or sample text
     * @param publishDate Publication date in ISO-8601 format
     */
    public Book(int id, String title, String description, int pageCount, String excerpt, String publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.excerpt = excerpt;
        this.publishDate = publishDate;
    }

    // -- Getters and Setters --

    /** @return Unique book identifier */
    public int getId() { return id; }

    /** @param id Unique book identifier */
    public void setId(int id) { this.id = id; }

    /** @return Book title */
    public String getTitle() { return title; }

    /** @param title Book title */
    public void setTitle(String title) { this.title = title; }

    /** @return Book description or summary */
    public String getDescription() { return description; }

    /** @param description Book description or summary */
    public void setDescription(String description) { this.description = description; }

    /** @return Total number of pages */
    public int getPageCount() { return pageCount; }

    /** @param pageCount Total number of pages */
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    /** @return Excerpt or sample text */
    public String getExcerpt() { return excerpt; }

    /** @param excerpt Excerpt or sample text */
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    /** @return Publication date in ISO-8601 format */
    public String getPublishDate() { return publishDate; }

    /** @param publishDate Publication date in ISO-8601 format */
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    // Optionally override toString(), equals(), hashCode() if needed for debugging or collections.
}