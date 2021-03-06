package cc.dcloud.domain.folder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cc.dcloud.domain.group.Group;
import lombok.Getter;

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

	public Folder(String name, Group group, Integer parentId) {
		this.name = name;
		this.group = group;
		this.parentId = parentId;
	}

	public Folder() {

	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
