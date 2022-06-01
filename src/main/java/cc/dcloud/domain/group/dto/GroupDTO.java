package cc.dcloud.domain.group.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDTO {

	private Integer id;
	private String name;
	private Integer rootFolderId;
	//    private List<Folder> folderList;

	public GroupDTO() {
	}

	public GroupDTO(Integer id, String name, Integer rootFolderId) {
		this.id = id;
		this.name = name;
		this.rootFolderId = rootFolderId;
	}
}
