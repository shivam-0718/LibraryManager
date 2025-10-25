package in.vyashivam.librarymanager.controller;

import in.vyashivam.librarymanager.model.Book;
import in.vyashivam.librarymanager.model.Loan;
import in.vyashivam.librarymanager.model.Member;
import in.vyashivam.librarymanager.service.IBookService;
import in.vyashivam.librarymanager.service.ILoanService;
import in.vyashivam.librarymanager.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private ILoanService loanService;

    @Autowired
    public void setLoanService(ILoanService loanService) {
        this.loanService = loanService;
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
}
