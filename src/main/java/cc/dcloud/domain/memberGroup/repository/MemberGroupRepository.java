package cc.dcloud.domain.memberGroup.repository;

import cc.dcloud.domain.memberGroup.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberGroupRepository extends JpaRepository<MemberGroup, Integer> {

    List<MemberGroup> findAllByMemberId(Integer memberId);

}
