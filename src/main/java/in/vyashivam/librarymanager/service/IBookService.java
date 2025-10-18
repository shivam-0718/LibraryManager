package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.model.Book;
import java.util.List;

public interface IBookService {
    Book registerBook(Book book);
    List<Book> getBooks();
    String updateBookDetails(Book book);
    Book getBookById(Long id);
    String deleteBook(Long id);
}
