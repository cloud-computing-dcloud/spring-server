package cc.dcloud.domain.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cc.dcloud.domain.login.dto.LoginDto;
import cc.dcloud.domain.login.dto.MemberDto;
import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.domain.login.dto.TokenDto;
import cc.dcloud.domain.login.service.LoginService;
import cc.dcloud.domain.login.util.JwtTokenUtil;

@RestController
@CrossOrigin("*")
public class LoginController {

	private final LoginService loginService;
	private final JwtTokenUtil jwtTokenUtil;

	public LoginController(LoginService loginService, JwtTokenUtil jwtTokenUtil) {
		this.loginService = loginService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping("/signUp")
	public ResponseEntity<SignUpDto> signUp(@RequestBody SignUpDto signUpDto) {
		loginService.signUp(signUpDto);
		return ResponseEntity.ok(signUpDto);
	}

	@PostMapping("/signUp/admin")
	public ResponseEntity<SignUpDto> signUpAdmin(@RequestBody SignUpDto signUpDto) {
		loginService.signUpAdmin(signUpDto);
		return ResponseEntity.ok(signUpDto);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
		TokenDto login = loginService.login(loginDto);
		return ResponseEntity.ok(login);
	}

	@GetMapping("/members/{username}")
	public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String username) {
		MemberDto memberInfo = loginService.getMemberInfo(username);
		return ResponseEntity.ok(memberInfo);
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
		TokenDto reissue = loginService.reissue(refreshToken);
		return ResponseEntity.ok(reissue);
	}

	@PostMapping("/logout")
	public ResponseEntity logout(@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		String username = jwtTokenUtil.getUsername(resolveToken(accessToken));
		loginService.logout(TokenDto.of(accessToken, refreshToken), username);

		return ResponseEntity.noContent().build();
	}

	private String resolveToken(String accessToken) {
		return accessToken.substring(7);
	}

}
