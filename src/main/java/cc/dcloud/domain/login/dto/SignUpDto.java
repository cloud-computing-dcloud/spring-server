package cc.dcloud.domain.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class SignUpDto {

    private String username;
    private String password;

    public SignUpDto() {
    }

    public SignUpDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
