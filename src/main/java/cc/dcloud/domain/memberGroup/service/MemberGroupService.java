package cc.dcloud.domain.memberGroup.service;

import cc.dcloud.domain.memberGroup.MemberGroup;

import java.util.List;

public interface MemberGroupService {
    MemberGroup create(Integer memberId, Integer groupId);

    List<MemberGroup> getByMemberId(Integer memberId);
}
