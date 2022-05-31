package cc.dcloud.domain.group.service;

import cc.dcloud.domain.folder.service.FolderService;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.GroupType;
import cc.dcloud.domain.group.dto.GroupDTO;
import cc.dcloud.domain.group.dto.GroupListDTO;
import cc.dcloud.domain.login.exception.NotFoundException;
import cc.dcloud.domain.group.repository.GroupRepository;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.member.repository.MemberRepository;
import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.memberGroup.repository.MemberGroupRepository;
import cc.dcloud.domain.memberGroup.service.MemberGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final MemberGroupService memberGroupService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final FolderService folderService;

    @Transactional
    public Group create(String name, GroupType groupType) {
        Group build = Group.create(name, groupType);
        Group group = groupRepository.save(build);

        return group;
    }

    @Transactional
    public Group createPublicGroup(Integer memberId, String name, GroupType groupType){
        memberRepository
                .findById(memberId)
                .orElseThrow(NotFoundException::new);
        Group build = Group.create(name, groupType);
        Group group = groupRepository.save(build);
        folderService.createRootFolder(group);
//        MemberGroup.create(memberId, group.getId());
        memberGroupService.create(memberId, group.getId());

        return group;
    }

    @Transactional
    public void join(Integer groupId, Integer memberId){
        // 로그인 인증 및 권한

        Group group = groupRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
        //group.join(memberId); // 이거 어떄?
    }

    // 회원이 참가한 그룹 리스트 return
    public GroupListDTO findAllByMember(Integer memberId){
        List<GroupDTO> groupList = new ArrayList<>();
        List<MemberGroup> memberGroupList = memberGroupService.getByMemberId(memberId);
        for(MemberGroup memberGroup : memberGroupList){
            Group group = groupRepository.findById(memberGroup.getGroupId())
                    .orElseThrow(NotFoundException::new);
            if(group.getGroupType().equals(GroupType.PRIVATE)) {
                continue;
            }

            GroupDTO groupDTO = GroupDTO.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .rootFolderId(group.getRootFolderId())
                    .build();
            groupList.add(groupDTO);
        }
        return GroupListDTO.builder()
                .groupDTOList(groupList)
                .build();
    }
}