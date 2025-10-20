package in.vyashivam.librarymanager.controller;

import in.vyashivam.librarymanager.model.Member;
import in.vyashivam.librarymanager.service.IMemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private IMemberService memberService;

    @Autowired
    public void setMemberService(IMemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@Valid @RequestBody Member member) {
        String response = memberService.registerMember(member);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("get-member/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateMember(@RequestBody Member updatedMember) {
        String response = memberService.updateMemberDetails(updatedMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        String response = memberService.deleteMember(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
