package cc.dcloud.domain.login.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDto {

    private String username;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
