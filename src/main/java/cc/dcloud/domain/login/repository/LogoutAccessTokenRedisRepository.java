package cc.dcloud.domain.login.repository;

import cc.dcloud.domain.login.pojo.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
