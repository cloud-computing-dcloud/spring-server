package cc.dcloud.domain.group.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class GroupListDTO {
	List<GroupDTO> groupDTOList;

	public GroupListDTO() {
	}


	public GroupListDTO(List<GroupDTO> groupDTOList) {
		this.groupDTOList = groupDTOList;
	}
}
