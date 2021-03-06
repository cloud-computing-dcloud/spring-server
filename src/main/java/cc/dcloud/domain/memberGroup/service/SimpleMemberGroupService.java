package cc.dcloud.domain.memberGroup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.memberGroup.repository.MemberGroupRepository;

@Service
@Transactional(readOnly = true)
public class SimpleMemberGroupService implements MemberGroupService {

	private final MemberGroupRepository memberGroupRepository;

	public SimpleMemberGroupService(MemberGroupRepository memberGroupRepository) {
		this.memberGroupRepository = memberGroupRepository;
	}

	@Override
	@Transactional
	public MemberGroup create(Integer memberId, Integer groupId) {
		MemberGroup memberGroup = MemberGroup.create(memberId, groupId);
		return memberGroupRepository.save(memberGroup);
	}

	@Override
	public List<MemberGroup> getByMemberId(Integer memberId) {
		return memberGroupRepository.findAllByMemberId(memberId);
	}

	@Override
	public Optional<MemberGroup> getByGroupIdAndMemberId(Integer groupId, Integer memberId) {
		return memberGroupRepository.findByGroupIdAndMemberId(groupId, memberId);
	}
}
