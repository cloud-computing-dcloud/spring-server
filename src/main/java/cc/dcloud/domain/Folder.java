package cc.dcloud.domain;


import cc.dcloud.domain.group.Group;

import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Folder {

	@Id
	@GeneratedValue
	@Column(name = "folder_id")
	private Integer id; //GET folders/{folder_id}

	@Column(name = "folder_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group; //소유 그룹. 접근권한 확인 시 필요

	@Column(name = "parent_Id")
	private Integer parentId; // 상위 폴더 -> findAllByParentId(paren_id) -> 하위폴더 목록 가져옴
	//폴더 삭제 시 dfs, stack 써서 삭제

	//파일 이름, id 목록

	public void setGroup(Group group) {
		this.group = group;
	}

	public Folder(String name, Group group, Integer parentId) {
		this.name = name;
		this.group = group;
		this.parentId = parentId;
	}

	public Folder() {

	}
}
