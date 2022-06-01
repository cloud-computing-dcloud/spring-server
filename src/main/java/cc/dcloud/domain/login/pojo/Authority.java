package cc.dcloud.domain.login.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import cc.dcloud.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String role;

	public Authority() {
	}

	public Authority(Long id, Member member, String role) {
		this.id = id;
		this.member = member;
		this.role = role;
	}

	public static Authority ofUser(Member member) {
		return Authority.builder()
			.role("ROLE_USER")
			.member(member)
			.build();
	}

	public static Authority ofAdmin(Member member) {
		return Authority.builder()
			.role("ROLE_ADMIN")
			.member(member)
			.build();
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
