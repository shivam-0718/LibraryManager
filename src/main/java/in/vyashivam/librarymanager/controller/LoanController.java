package in.vyashivam.librarymanager.controller;

import in.vyashivam.librarymanager.model.Book;
import in.vyashivam.librarymanager.model.Loan;
import in.vyashivam.librarymanager.model.Member;
import in.vyashivam.librarymanager.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private ILoanService loanService;

    @Autowired
    public void setLoanService(ILoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loansList = loanService.getAllLoans();
        return new ResponseEntity<>(loansList, HttpStatus.OK);
    }

    @GetMapping("/rent-a-book/{memberId}/{bookId}")
    public ResponseEntity<String> rentABook(@PathVariable Long memberId, @PathVariable Long bookId) {
        Loan loan = loanService.borrowABook(memberId, bookId);
        Member member = loan.getMember();
        Book book = loan.getBook();
        String response = String.format("""
                Loan has been issued with the
                details as follows:
                
                Name: %s
                MemberId: %s
                MemberShip Status: %s
                Loan Id: %s
                Book Title: %s
                Issue Date: %s
                Return Date: %s
                
                """, member.getMemberName(), memberId,
                member.getMembershipStatus(), loan.getLoanId() , book.getTitle(),
                loan.getIssueDate(), loan.getDueDate());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/return-a-book/{loanId}")
    public ResponseEntity<String> returnABook(@PathVariable Long loanId) {
        Loan completedLoan = loanService.returnABook(loanId);
        String response = String.format("""
                Loan has been completed and the
                book has been returned with the
                details as follows:
                
                Name: %s
                MemberId: %s
                Loan Id: %s
                Book Title: %s
                Issue Date: %s
                Return Date: %s
                Due Date: %s
                Fine Amount: %s
                Total Amount: %s
                
                Thank you! Please visit again!
                """, completedLoan.getMember().getMemberName(), completedLoan.getMember().getMemberId(),
                loanId, completedLoan.getBook().getTitle(), completedLoan.getIssueDate(),
                completedLoan.getReturnDate(), completedLoan.getDueDate(), completedLoan.getFineAmount(),
                completedLoan.getTotalAmount());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
