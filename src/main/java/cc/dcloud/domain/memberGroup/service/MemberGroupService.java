package cc.dcloud.domain.memberGroup.service;

import java.util.List;
import java.util.Optional;

import cc.dcloud.domain.memberGroup.MemberGroup;

public interface MemberGroupService {
	MemberGroup create(Integer memberId, Integer groupId);

	List<MemberGroup> getByMemberId(Integer memberId);

	Optional<MemberGroup> getByGroupIdAndMemberId(Integer groupId, Integer memberId);
}
