package cc.dcloud.domain.member.service;

import cc.dcloud.domain.Member;
import cc.dcloud.domain.login.dto.SignUpDto;

public interface MemberService {

    Member signUp(SignUpDto signUpDto);

    Member signUpAdmin(SignUpDto signUpDto);

    Member getByUsername(String username);

}
