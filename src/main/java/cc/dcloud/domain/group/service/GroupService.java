package cc.dcloud.domain.group.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.folder.service.FolderService;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.group.GroupType;
import cc.dcloud.domain.group.dto.GroupDTO;
import cc.dcloud.domain.group.dto.GroupListDTO;
import cc.dcloud.domain.group.repository.GroupRepository;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.member.repository.MemberRepository;
import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.memberGroup.service.MemberGroupService;
import cc.dcloud.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

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
	public Group createPublicGroup(Integer memberId, String name, GroupType groupType) {
		//        memberRepository.findById(memberId)
		//                .orElseThrow(NotFoundException::new);
		Group build = Group.create(name, groupType);
		Group group = groupRepository.save(build);
		folderService.createRootFolder(group);
		memberGroupService.create(memberId, group.getId());

		return group;
	}

	@Transactional
	public void invite(Integer groupId, String username) {
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(NotFoundException::new);

		memberGroupService.getByGroupIdAndMemberId(member.getId(), groupId)
			.orElse(memberGroupService.create(member.getId(), groupId));
	}

	// 회원이 참가한 그룹 리스트 return
	public GroupListDTO findAllByMember(Integer memberId) {
		List<GroupDTO> groupList = new ArrayList<>();
		System.out.println("========memeber : {} -> " + memberId);
		List<MemberGroup> memberGroupList = memberGroupService.getByMemberId(memberId);
		for (MemberGroup memberGroup : memberGroupList) {
			Group group = groupRepository.findById(memberGroup.getGroupId())
				.orElseThrow(NotFoundException::new);
			System.out.println("=-======group : " + group);
			System.out.println("=========group typoe : " + group.getGroupType());
			if (group.getGroupType().equals(GroupType.PRIVATE)) {
				continue;
			}

		GroupDTO groupDTO = GroupDTO.builder()
				.id(group.getId())
				.name(group.getName())
				.rootFolderId(group.getRootFolderId())
				.build();
			groupList.add(groupDTO);
			System.out.println(groupDTO.getName());
		}
		return GroupListDTO.builder()
			.groupDTOList(groupList)
			.build();
	}

    public Group findByGroupId(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
    }

	public Integer findByRootFolderId(Integer folderId) {
		Group group = groupRepository.findByRootFolderId(folderId)
				.orElseThrow(NotFoundException::new);
		return group.getId();
	}

}