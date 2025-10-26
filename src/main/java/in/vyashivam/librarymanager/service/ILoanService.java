package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.model.Loan;
import java.util.List;

public interface ILoanService {
    List<Loan> getAllLoans();
    Loan borrowABook(Long memberId, Long bookId);
    Loan returnABook(Long loanId);
}
