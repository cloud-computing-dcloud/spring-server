package cc.dcloud.domain.member.service;

import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.domain.member.Member;

public interface MemberService {

	Member signUp(SignUpDto signUpDto);

	Member signUpAdmin(SignUpDto signUpDto);

	Member getByUsername(String username);

}
