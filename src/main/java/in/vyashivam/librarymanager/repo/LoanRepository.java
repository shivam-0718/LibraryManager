package in.vyashivam.librarymanager.repo;

import in.vyashivam.librarymanager.model.Loan;
import in.vyashivam.librarymanager.model.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    int countByMember_MemberIdAndStatus(Long memberId, ReturnStatus status);
}
