package in.vyashivam.librarymanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Book ID")
    private Long bookId;

    @Column(name = "Title", nullable = false, length = 100)
    @NotBlank(message = "Title cannot be null. It must be between 1 to 100 characters.")
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "Author", nullable = false, length = 50)
    @NotBlank(message = "Name of the author cannot be null. It must be between 1 to 50 characters.")
    @Size(min = 1, max = 50)
    private String author;

    @Column(name = "ISBN", nullable = false)
    @NotBlank(message = "ISBN cannot be empty.")
    @Pattern(regexp = "^(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+$", message = "Enter proper ISBN of the book.") // referred online
    private String isbn;

    @Column(name = "Genre", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Genre of the book is required.")
    private Genre genre;

    @Column(name = "Is Available", nullable = false)
    @NotNull(message = "Book can either be available or not available.")
    private Boolean isAvailable;

    @Column(name = "Total Copies")
    @Min(value = 0)
    private Integer totalCopies = 0;

    @Column(name = "Available Copies")
    @Min(value = 0)
    private Integer availableCopies = 0;
}
