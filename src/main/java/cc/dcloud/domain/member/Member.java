package cc.dcloud.domain.member;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.exception.NotMatchPwdException;
import cc.dcloud.domain.login.pojo.Authority;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Builder
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Integer id;

	@Column(unique = true)
	private String username; // login id

	private String password;

//	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//	@Builder.Default
//	private List<MemberGroup> memberGroupList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<Authority> authorities = new HashSet<>();

	public Member() {
	}

	public Member(Integer id, String username, String password, Set<Authority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static Member ofUser(SignUpDto signUpDto) {
		Member member = Member.builder()
				.username(signUpDto.getUsername())
				.password(signUpDto.getPassword())
				.build();
		member.addAuthority(Authority.ofUser(member));
		return member;
	}

	public static Member ofAdmin(SignUpDto signUpDto) {
		Member member = Member.builder()
				.username(signUpDto.getUsername())
				.password(signUpDto.getPassword())
				.build();
		member.addAuthority(Authority.ofAdmin(member));
		return member;
	}

	public List<String> getRoles() {
		return authorities.stream()
				.map(Authority::getRole)
				.collect(Collectors.toList());
	}

	private void addAuthority(Authority authority) {
		authorities.add(authority);
	}

	public void checkPassword(PasswordEncoder passwordEncoder, String comparePassword) {
		if(!passwordEncoder.matches(comparePassword, this.password)) {
			throw new NotMatchPwdException();
		}
	}

}
