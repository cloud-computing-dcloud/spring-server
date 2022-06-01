package cc.dcloud.domain.memberGroup.repository;

import cc.dcloud.domain.memberGroup.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MemberGroupRepository extends JpaRepository<MemberGroup, Integer> {

    List<MemberGroup> findAllByMemberId(Integer memberId);

    @Query("select mg from MemberGroup mg where mg.groupId = :groupId and mg.memberId = :memberId")
    Optional<MemberGroup> findByGroupIdAndMemberId(@Param("groupId") Integer groupId, @Param("memberId") Integer memberId);
}
