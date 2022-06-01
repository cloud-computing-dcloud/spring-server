package cc.dcloud.domain.login.pojo;

import cc.dcloud.exception.NotMatchTokenException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@RedisHash("refreshToken")
@Builder
@Getter
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String username, String refreshToken, Long remainingMilliSeconds) {
        return RefreshToken.builder()
                .id(username)
                .refreshToken(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

    public void checkToken(String compareToken) {
        if(!compareToken.equals(refreshToken)) {
            throw new NotMatchTokenException();
        }
    }

}
