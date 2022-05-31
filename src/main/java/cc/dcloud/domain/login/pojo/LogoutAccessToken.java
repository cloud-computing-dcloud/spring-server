package cc.dcloud.domain.login.pojo;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("logoutAccessToken") // 선언된 value로 redis 의 set 자료구조를 통해 해당 객체 저장
@Builder
public class LogoutAccessToken {

    @Id
    private String id;

    private String username;

    @TimeToLive // 설정한 시간 만큼 데이터 저장, 설정한 시간이 지나면 자동으로 데이터 사라짐
    private Long expiration;

    public static LogoutAccessToken of(String accessToken, String username, Long remainingMilliSeconds) {
        return LogoutAccessToken.builder()
                .id(accessToken)
                .username(username)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

}
