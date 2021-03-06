package cc.dcloud.domain.group.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.group.GroupType;
import cc.dcloud.domain.group.dto.CreateGroupDTO;
import cc.dcloud.domain.group.dto.GroupListDTO;
import cc.dcloud.domain.group.dto.InviteForm;
import cc.dcloud.domain.group.service.GroupService;
import cc.dcloud.domain.login.util.JwtTokenUtil;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class GroupController {

	private final MemberService memberService;
	private final GroupService groupService;
	private final JwtTokenUtil jwt;

	@PostMapping("/group/create")
	public ResponseEntity<Group> create(@RequestBody CreateGroupDTO createGroupDTO,
										@RequestHeader String authorization) {
		Member member = getMemberByToken(authorization);
		//일단 Group 엔티티 자체 return하도록
		Group group = groupService.createPublicGroup(
				member.getId(),
			createGroupDTO.getName(),
			GroupType.PUBLIC);
		return ResponseEntity.ok(group);
	}

	@PostMapping("/group/{rootDirectory}/join")
	public ResponseEntity<String> join(@PathVariable("rootDirectory") Integer folderId, @RequestBody InviteForm form) {
		int groupId = getGroupByFolder(folderId);
		groupService.invite(groupId, form.getUsername());
		return ResponseEntity.ok("success");
	}

	@GetMapping("/groups")
	public ResponseEntity<GroupListDTO> groups(@RequestHeader String authorization) {
		Member member = getMemberByToken(authorization);
		GroupListDTO groupList = groupService.findAllByMember(member.getId());
		return ResponseEntity.ok(groupList);
	}

	private int getGroupByFolder(Integer folderId) {
		return groupService.findByRootFolderId(folderId);
	}

	private Member getMemberByToken(String authorization) {
		String token = authorization.substring(7);
		return memberService.getByUsername(jwt.getUsername(token));
	}
}
