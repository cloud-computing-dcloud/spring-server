package cc.dcloud.domain.group.controller;

import cc.dcloud.domain.GroupType;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.group.dto.CreateGroupDTO;
import cc.dcloud.domain.group.dto.GroupListDTO;
import cc.dcloud.domain.group.dto.InviteForm;
import cc.dcloud.domain.group.service.GroupService;
import cc.dcloud.domain.login.util.JwtTokenUtil;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final JwtTokenUtil jwt;

    @PostMapping("/group/create")
    public ResponseEntity<Group> create(@RequestBody CreateGroupDTO createGroupDTO){

        //일단 Group 엔티티 자체 return하도록
        Group group = groupService.createPublicGroup(
                createGroupDTO.getMemberId(),
                createGroupDTO.getName(),
                GroupType.PUBLIC);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/group/{groupId}/join")
    public ResponseEntity<String> join(@PathVariable("groupId")Integer groupId, @RequestBody InviteForm form){
        groupService.invite(groupId, form.getUsername());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/groups")
    public ResponseEntity<GroupListDTO> groups(@RequestHeader String authorization){
        Member member = getMemberByToken(authorization);
        GroupListDTO groupList = groupService.findAllByMember(member.getId());
        return ResponseEntity.ok(groupList);
    }

    private Member getMemberByToken(String authorization) {
        String token = authorization.substring(7);
        return memberService.getByUsername(jwt.getUsername(token));
    }
}
