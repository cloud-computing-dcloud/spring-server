package cc.dcloud.domain.login.dto;

import cc.dcloud.domain.login.util.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private Integer rootFolderId;

    public static LoginResponseDto of(String accessToken, String refreshToken, Integer rootFolderId) {
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .rootFolderId(rootFolderId)
                .build();
    }
}
