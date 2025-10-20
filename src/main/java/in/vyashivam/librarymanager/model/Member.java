package in.vyashivam.librarymanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Member ID")
    private Long memberId;

    @Column(name = "Email ID")
    @Email(message = "Please enter a valid email id.")
    private String emailId;

    @Column(name = "Membership Status")
    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;

    @PastOrPresent(message= "Membership start date cannot be of future")
    @Column(name = "Membership Start Date")
    private LocalDate membershipStartDate;

    @Column(name = "Membership End Date")
    private LocalDate membershipEndDate;

    @Column(name = "Contact Number")
    @Pattern(regexp = "^\\+\\d{1,3}[\\s-]?\\d{10}$", message = "Number should follow the format +(Country Code) followed by 10 digit number.")
    private String contact;
}
