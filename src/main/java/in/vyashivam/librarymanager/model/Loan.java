package in.vyashivam.librarymanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Loan ID")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "Member ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "Book ID")
    private Book book;

    @PastOrPresent
    private LocalDate issueDate; // create it automatically

    private LocalDate dueDate; // 14 days from the issue date -> must be created automatically
    private LocalDate returnDate; // it needs to be compared with issue date, and must be before due Date to avaid fine.

    @Enumerated(EnumType.STRING)
    @Column(name = "Return Status")
    private ReturnStatus status;

    @Column(name = "Fine Amount")
    @Min(value = 0)
    private BigDecimal fineAmount;
}
