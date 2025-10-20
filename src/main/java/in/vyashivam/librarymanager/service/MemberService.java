package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.exception.BookNotFoundException;
import in.vyashivam.librarymanager.exception.InvalidMembershipEndDateException;
import in.vyashivam.librarymanager.exception.MemberNotFoundException;
import in.vyashivam.librarymanager.model.Member;
import in.vyashivam.librarymanager.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements IMemberService {
    private MemberRepository memberRepo;

    @Autowired
    public void setMemberRepo(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public String registerMember(Member member) {
        if(!member.getMembershipEndDate().isAfter(member.getMembershipStartDate())) {
            throw new InvalidMembershipEndDateException("Membership End Date cannot be before Membership Start Date.");
        }
        memberRepo.save(member);
        return "Member has been registered in the library portal with the id number of " + member.getMemberId();
    }

    @Override
    public List<Member> getMembers() {
        return memberRepo.findAll();
    }

    @Override
    public String updateMemberDetails(Member member) {
        Optional<Member> optional = memberRepo.findById(member.getMemberId());
        if (optional.isPresent()) {
            Member updatedMember  = memberRepo.save(member);
            return "Book with the given id " + member.getMemberId() + " has been updated successfully!";
        }
        throw new MemberNotFoundException("Member with given id is not available in the database. Please try again.");
    }

    @Override
    public Member getMemberById(Long id) {
        Optional<Member> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new MemberNotFoundException("Member with given id is not available in the database. Please try again.");
    }

    @Override
    public String deleteMember(Long id) {
        Optional<Member> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            memberRepo.deleteById(id);
            return "Book with the given id" + id + " has been deleted successfully!";
        }
        throw new BookNotFoundException("Book with given id is not available. Please try again.");
    }
}
