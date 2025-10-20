package in.vyashivam.librarymanager.controller;

import in.vyashivam.librarymanager.model.Book;
import in.vyashivam.librarymanager.service.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private IBookService bookService;

    @Autowired
    public void setBookService(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerBook(@Valid @RequestBody Book book) {
        String response = bookService.registerBook(book);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("get-book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBook(@RequestBody Book updatedBook) {
        String response = bookService.updateBookDetails(updatedBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        String response = bookService.deleteBook(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
