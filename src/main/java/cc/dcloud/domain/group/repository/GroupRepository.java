package cc.dcloud.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.dcloud.domain.group.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
