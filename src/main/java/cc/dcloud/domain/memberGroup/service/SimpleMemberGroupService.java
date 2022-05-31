package cc.dcloud.domain.memberGroup.service;


import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.memberGroup.repository.MemberGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
