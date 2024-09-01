import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibrarySystem {
    private Map<String, Book> books;

    public LibrarySystem() {
        this.books = new HashMap<>();
    }

    // Add a book
    public boolean addBook(String identifier, String title, String author, int publicationYear) {
        if (books.containsKey(identifier)) {
            return false; // Book with this identifier already exists
        } else {
            books.put(identifier, new Book(identifier, title, author, publicationYear, true));
            return true;
        }
    }

    // Borrow a book
    public boolean borrowBook(String identifier) {
        if (books.containsKey(identifier)) {
            Book book = books.get(identifier);
            if (book.isAvailable()) {
                book.setAvailable(false);
                return true;
            } else {
                return false; // Book is not available
            }
        } else {
            throw new IllegalArgumentException("Book does not exist"); // Book does not exist
        }
    }

    // Return a book
    public boolean returnBook(String identifier) {
        if (books.containsKey(identifier)) {
            Book book = books.get(identifier);
            if (!book.isAvailable()) {
                book.setAvailable(true);
                return true;
            } else {
                return false; // Book was not borrowed
            }
        }
        return false; // Book does not exist
    }

    // View available books
    public List<Book> viewAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    // Inner Book class
    public static class Book {
        private String identifier;
        private String title;
        private String author;
        private int publicationYear;
        private boolean available;

        public Book(String identifier, String title, String author, int publicationYear, boolean available) {
            this.identifier = identifier;
            this.title = title;
            this.author = author;
            this.publicationYear = publicationYear;
            this.available = available;
        }

        public String getIdentifier() { return identifier; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public int getPublicationYear() { return publicationYear; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
    }
}
