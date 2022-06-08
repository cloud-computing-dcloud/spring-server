package cc.dcloud.domain.group.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CreateGroupDTO {
	String name;

	public CreateGroupDTO() {
	}

	public CreateGroupDTO(String name) {
		this.name = name;
	}
}