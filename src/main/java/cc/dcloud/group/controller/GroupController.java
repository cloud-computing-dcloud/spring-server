package cc.dcloud.group.controller;

import cc.dcloud.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // Spring MVC 몰라서 일단 String으로 return받게 해놨음
    @PostMapping("/group/create")
    public String create(){
        return "/";
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
