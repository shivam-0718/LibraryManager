package in.vyashivam.librarymanager.service;

import in.vyashivam.librarymanager.model.Member;
import java.util.List;

public interface IMemberService {
    String registerMember(Member member);
    List<Member> getMembers();
    String updateMemberDetails(Member member);
    Member getMemberById(Long id);
    String deleteMember(Long id);
}
