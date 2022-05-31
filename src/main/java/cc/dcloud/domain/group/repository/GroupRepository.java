package cc.dcloud.domain.group.repository;

import cc.dcloud.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
