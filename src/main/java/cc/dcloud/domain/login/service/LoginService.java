package cc.dcloud.domain.login.service;

import cc.dcloud.domain.login.dto.LoginDto;
import cc.dcloud.domain.login.dto.MemberDto;
import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.domain.login.dto.TokenDto;

public interface LoginService {

    void signUp(SignUpDto signUpDto);

    void signUpAdmin(SignUpDto signUpDto);

    TokenDto login(LoginDto loginDto);

    MemberDto getMemberInfo(String username);

    void logout(TokenDto tokenDto, String username);

    TokenDto reissue(String refreshToken);

}
