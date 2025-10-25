package in.vyashivam.librarymanager.repo;

import in.vyashivam.librarymanager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Returning list of members (if found with same name)
    List<Member> findByMemberNameContainingIgnoreCase(String name);

    // finding a member by email id
    Optional<Member> findByEmailId(String emailId);

}
