package cc.dcloud.domain.login.repository;

import org.springframework.data.repository.CrudRepository;

import cc.dcloud.domain.login.pojo.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
