package cc.dcloud.domain.login.service;

import cc.dcloud.domain.login.dto.*;

public interface LoginService {

    void signUp(SignUpDto signUpDto);

    void signUpAdmin(SignUpDto signUpDto);

    LoginResponseDto login(LoginDto loginDto);

    MemberDto getMemberInfo(String username);

    void logout(TokenDto tokenDto, String username);

    TokenDto reissue(String refreshToken);

}
