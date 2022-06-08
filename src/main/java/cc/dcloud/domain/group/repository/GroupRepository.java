package cc.dcloud.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.dcloud.domain.group.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    Optional<Group> findByRootFolderId(Integer rootFolderId);

}
