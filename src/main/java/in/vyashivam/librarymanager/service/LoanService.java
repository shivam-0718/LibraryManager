package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.exception.*;
import in.vyashivam.librarymanager.model.*;
import in.vyashivam.librarymanager.repo.BookRepository;
import in.vyashivam.librarymanager.repo.LoanRepository;
import in.vyashivam.librarymanager.repo.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService implements ILoanService{
    private BookRepository bookRepo;
    private MemberRepository memberRepo;
    private LoanRepository loanRepo;

    @Autowired
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Autowired
    public void setMemberRepo(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Autowired
    public void setLoanRepo(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

    @Override
    @Transactional
    public Loan borrowABook(Long memberId, Long bookId) {
        Optional<Member> optionalMember = memberRepo.findById(memberId);
        if(optionalMember.isEmpty()) {
            throw new MemberNotFoundException("Member does not exist.");
        }

        Member member = optionalMember.get();
        if(member.getMembershipStatus().equals(MembershipStatus.INACTIVE) || member.getMembershipStatus().equals(MembershipStatus.EXPIRED)) {
            throw new MembershipStatusInactiveException("Membership is inactive or expired. Kindly renew the membership first.");
        }

        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if(optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book does not exist");
        }

        Book book = optionalBook.get();
        if(book.getAvailableCopies() == 0) {
            throw new BookIsUnavailableException("Book is temporarily unavailable. Kindly wait for few more days.");
        }

        int countOfLoans = loanRepo.countByMember_MemberIdAndStatus(memberId, ReturnStatus.ISSUED);
        if(countOfLoans >= 3) {
            throw new MaxActiveLoansExceededException("Just pay the remaining active loans.");
        }

        Loan newLoan = new Loan();
        newLoan.setIssueDate(LocalDate.now());
        newLoan.setDueDate(newLoan.getIssueDate().plusDays(14));
        newLoan.setStatus(ReturnStatus.ISSUED);
        newLoan.setMember(member);
        newLoan.setBook(book);
        newLoan.setRentFees(BigDecimal.valueOf(500.00));

        int recentAvailableCopies = book.getAvailableCopies() - 1;
        book.setAvailableCopies(recentAvailableCopies);
        bookRepo.save(book);

        loanRepo.save(newLoan);
        return newLoan;
    }

    @Override
    @Transactional
    public Loan returnABook(Long loanId) {
        Optional<Loan> loanInfo = loanRepo.findById(loanId);
        if(loanInfo.isEmpty()) {
            throw new LoanNotFoundException("Loan with the given id does not exist.");
        }

        Loan loanDetails = loanInfo.get();

        if(loanDetails.getStatus().equals(ReturnStatus.RETURNED)) {
            throw new BookAlreadyReturnedException("Book has been already returned.");
        }
        else if(loanDetails.getStatus().equals(ReturnStatus.ISSUED)) {
            loanDetails.setReturnDate(LocalDate.now());

            LocalDate dueDate = loanDetails.getDueDate();
            LocalDate returnDate = loanDetails.getReturnDate();

            if(returnDate.isBefore(dueDate)) {
                loanDetails.setFineAmount(BigDecimal.ZERO);
                loanDetails.setTotalAmount(loanDetails.getRentFees().add(loanDetails.getFineAmount()));
                loanDetails.setStatus(ReturnStatus.RETURNED);
            } else {
                int daysBetween = (int) ChronoUnit.DAYS.between(returnDate, dueDate);
                loanDetails.setFineAmount(BigDecimal.valueOf(30.00).multiply(BigDecimal.valueOf(daysBetween)));
                loanDetails.setTotalAmount(loanDetails.getRentFees().add(loanDetails.getFineAmount()));
                loanDetails.setStatus(ReturnStatus.RETURNED_WITH_FINE);
            }

            loanRepo.save(loanDetails);
        }

        return loanDetails;
    }
}
