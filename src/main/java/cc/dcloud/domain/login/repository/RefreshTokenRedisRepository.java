package cc.dcloud.domain.login.repository;

import cc.dcloud.domain.login.pojo.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
