package cc.dcloud.domain.login.dto;

import cc.dcloud.domain.login.util.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return TokenDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
