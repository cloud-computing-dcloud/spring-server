package cc.dcloud.domain.login.dto;

import cc.dcloud.domain.memberGroup.MemberGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberDto {

    private String username;
    private List<MemberGroup> memberGroupList;

    public MemberDto() {
    }

    public MemberDto(String username, List<MemberGroup> memberGroupList) {
        this.username = username;
        this.memberGroupList = memberGroupList;
    }
}
