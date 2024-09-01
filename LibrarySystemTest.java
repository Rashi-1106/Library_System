import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class LibrarySystemTest {
    private LibrarySystem library;

    @Before
    public void setUp() {
        library = new LibrarySystem();
    }
    

    //TEST TO ADD NEW BOOK
    @Test
    public void testAddNewBookSuccessfully() {
        boolean result = library.addBook("1234567890", "Book Title", "Book Author", 2024);
        assertTrue(result); // Check if the book was added successfully

        LibrarySystem.Book book = library.viewAvailableBooks().get(0);
        assertNotNull(book); // Ensure the book is in the library
        assertEquals("1234567890", book.getIdentifier());
        assertEquals("Book Title", book.getTitle());
        assertEquals("Book Author", book.getAuthor());
        assertEquals(2024, book.getPublicationYear());
        assertTrue(book.isAvailable()); // Check if the book is available
    }


    //TEST TO ADD BOOK WITH DUPLICATE IDENTIFIER 
    @Test
    public void testAddBookWithDuplicateIdentifier() {
        library.addBook("1234567890", "Book Title", "Book Author", 2024);
        boolean result = library.addBook("1234567890", "Another Title", "Another Author", 2025);
        assertFalse(result); // Check if the system rejects adding a book with a duplicate identifier
        }


    
 
    //TEST TO BORROW AVAILABLE BOOKS
    @Test
    public void testBorrowAvailableBook() {
        library.addBook("1234567890", "Book Title", "Book Author", 2024);
        boolean result = library.borrowBook("1234567890");
        assertTrue(result); // Check if borrowing was successful

    }

    //TEST TO BORROW UNAVAILABLE BOOK
    @Test
    public void testBorrowUnavailableBook() {
        library.addBook("1234567890", "Book Title", "Book Author", 2024);
        library.borrowBook("1234567890");
        boolean result = library.borrowBook("1234567890"); // Attempt to borrow it again
        assertFalse(result); // Check if borrowing an already borrowed book fails
    }

   


    //TEST TO BORROW NONEXISTENT BOOK
    @Test(expected = IllegalArgumentException.class)
    public void testBorrowNonExistentBookS() {
        library.borrowBook("9876543210"); // Attempt to borrow a book that does not exist
        boolean result = library.borrowBook("9876543210");
        assertTrue(result);
    }
    


    //TEST TO RETURN A BOOK
    @Test
    public void testReturnBook() {
        library.addBook("1234567890", "Book Title", "Book Author", 2024);
        library.borrowBook("1234567890");
        boolean result = library.returnBook("1234567890");
        assertTrue(result); // Check if returning the book was successful

        LibrarySystem.Book book = library.viewAvailableBooks().stream()
                                         .filter(b -> b.getIdentifier().equals("1234567890"))
                                         .findFirst()
                                         .orElse(null);
        assertNotNull(book);
        assertTrue(book.isAvailable()); // Check if the book is available again
    }

    //TEST TO CHECK RETURNING A BOOK WHICH WAS NOT BORROWED 
    @Test
    public void testReturnBookNotBorrowed() {
        library.addBook("1234567890", "Book Title", "Book Author", 2024);
        boolean result = library.returnBook("1234567890"); // Attempt to return a book that was not borrowed
        assertFalse(result); // Check if returning a non-borrowed book fails
        }



    //TEST TO VIEW AVAILABLE BOOKS IN THE SYSTEM
    @Test
    public void testViewAvailableBooks() {
        library.addBook("1234567890", "Book Title 1", "Author 1", 2021);
        library.addBook("0987654321", "Book Title 2", "Author 2", 2022);
        library.borrowBook("1234567890");

        List<LibrarySystem.Book> availableBooks = library.viewAvailableBooks();
        assertEquals(1, availableBooks.size()); // There should be 1 available book

        LibrarySystem.Book book = availableBooks.get(0);
        assertEquals("0987654321", book.getIdentifier());
        assertEquals("Book Title 2", book.getTitle());
        assertEquals("Author 2", book.getAuthor());
        assertEquals(2022, book.getPublicationYear());
        assertTrue(book.isAvailable()); // Check if the book is available
    }
}
