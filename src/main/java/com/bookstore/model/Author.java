package com.bookstore.model;

/**
 * Represents an Author entity as defined in the Bookstore API.
 * <p>
 * This Plain Old Java Object (POJO) maps directly to the JSON structure
 * returned and accepted by the /Authors endpoints.
 * Designed for use in serialization/deserialization and for building test data.
 * </p>
 * <p>
 * <b>Fields:</b>
 * <ul>
 *   <li><b>id</b> - Unique identifier for the author.</li>
 *   <li><b>name</b> - Full name of the author.</li>
 *   <li><b>bio</b> - Short biography of the author.</li>
 *   <li><b>birthDate</b> - Author's birth date in YYYY-MM-DD format.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Usage Example:<br>
 * <code>
 *   Author author = new Author(1, "Jane Austen", "Famous novelist", "1775-12-16");
 * </code>
 * </p>
 */
public class Author {

    /** Unique identifier for each author. */
    private int id;

    /** Full name of the author. */
    private String name;

    /** Short biography of the author. */
    private String bio;

    /** Author's birth date in YYYY-MM-DD format. */
    private String birthDate;

    /**
     * Default no-args constructor required for deserialization.
     */
    public Author() { }

    /**
     * All-args constructor for easy creation of Author objects.
     *
     * @param id        Unique author identifier
     * @param name      Full name of the author
     * @param bio       Short biography of the author
     * @param birthDate Author's birth date in YYYY-MM-DD format
     */
    public Author(int id, String name, String bio, String birthDate) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    // -- Getters and Setters --

    /** @return Unique author identifier */
    public int getId() { return id; }

    /** @param id Unique author identifier */
    public void setId(int id) { this.id = id; }

    /** @return Full name of the author */
    public String getName() { return name; }

    /** @param name Full name of the author */
    public void setName(String name) { this.name = name; }

    /** @return Short biography of the author */
    public String getBio() { return bio; }

    /** @param bio Short biography of the author */
    public void setBio(String bio) { this.bio = bio; }

    /** @return Author's birth date in YYYY-MM-DD format */
    public String getBirthDate() { return birthDate; }

    /** @param birthDate Author's birth date in YYYY-MM-DD format */
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    // Optionally override toString(), equals(), hashCode() if needed for debugging or collections.
}