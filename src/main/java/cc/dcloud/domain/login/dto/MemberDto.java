package cc.dcloud.domain.login.dto;

import java.util.List;

import cc.dcloud.domain.memberGroup.MemberGroup;
import lombok.Builder;
import lombok.Getter;

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
