package cc.dcloud.domain.group.controller;

import cc.dcloud.domain.GroupType;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.group.dto.CreateGroupDTO;
import cc.dcloud.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

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
    public String join(@PathVariable("groupId")Integer groupId){
        return "/";
    }

    @GetMapping("/groups")
    public String groups(){
        return "/";
    }

}
