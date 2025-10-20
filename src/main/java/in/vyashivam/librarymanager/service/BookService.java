package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.exception.BookNotFoundException;
import in.vyashivam.librarymanager.model.Book;
import in.vyashivam.librarymanager.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{
    private BookRepository bookRepo;

    @Autowired
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public String registerBook(Book book) {
        bookRepo.save(book);
        return "Book has been registered in the library with the id number of " + book.getBookId();
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> optional = bookRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BookNotFoundException("Book with given id is not available. Please try again.");
    }


    @Override
    public String updateBookDetails(Book book) {
        Optional<Book> optional = bookRepo.findById(book.getBookId());
        if (optional.isPresent()) {
            Book updatedBook  = bookRepo.save(book);
            return "Book with the given id " + book.getBookId() + " has been updated successfully!";
        }
        throw new BookNotFoundException("Book with given id is not available. Please try again.");
    }

    @Override
    public String deleteBook(Long id) {
        Optional<Book> optional = bookRepo.findById(id);
        if (optional.isPresent()) {
            bookRepo.deleteById(id);
            return "Book with the given id" + id + " has been deleted successfully!";
        }
        throw new BookNotFoundException("Book with given id is not available. Please try again.");
    }
}
