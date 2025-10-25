package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.model.Loan;

public interface ILoanService {
    Loan borrowABook(Long memberId, Long bookId);
}
