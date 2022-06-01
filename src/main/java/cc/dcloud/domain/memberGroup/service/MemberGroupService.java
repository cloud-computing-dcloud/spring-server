package cc.dcloud.domain.memberGroup.service;

import cc.dcloud.domain.memberGroup.MemberGroup;

import java.util.List;
import java.util.Optional;

public interface MemberGroupService {
    MemberGroup create(Integer memberId, Integer groupId);

    List<MemberGroup> getByMemberId(Integer memberId);

    Optional<MemberGroup> getByGroupIdAndMemberId(Integer groupId, Integer memberId);
}
