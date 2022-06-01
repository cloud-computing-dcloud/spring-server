package cc.dcloud.domain.login.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cc.dcloud.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;

	@Builder.Default
	private List<String> roles = new ArrayList<>();

	public CustomUserDetails() {
	}

	public CustomUserDetails(String username, String password, List<String> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public static CustomUserDetails of(Member member) {
		return CustomUserDetails.builder()
			.username(member.getUsername())
			.password(member.getPassword())
			.roles(member.getRoles())
			.build();
	}

	// 저장할 때 관련이 없는 나머지들은 JsonIgnore 처리

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return false;
	}
}
