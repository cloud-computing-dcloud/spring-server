package cc.dcloud.domain.login.repository;

import org.springframework.data.repository.CrudRepository;

import cc.dcloud.domain.login.pojo.LogoutAccessToken;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
