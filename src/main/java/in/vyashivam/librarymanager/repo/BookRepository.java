package in.vyashivam.librarymanager.repo;

import in.vyashivam.librarymanager.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Returning list of books containing title.
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Returning list of books containing author name.
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // finding a book by Isbn
    Optional<Book> findByIsbn(String isbn);
}
