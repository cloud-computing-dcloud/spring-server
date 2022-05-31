package cc.dcloud.group.service;

import cc.dcloud.domain.Group;
import cc.dcloud.domain.GroupType;
import cc.dcloud.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Group create(Integer memberId, String name, GroupType groupType) {
        Group group = Group.create(memberId, name, groupType);
        groupRepository.save(group);
        return group;
    }
}