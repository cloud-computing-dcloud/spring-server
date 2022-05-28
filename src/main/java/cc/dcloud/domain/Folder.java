package cc.dcloud.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Folder {

	@Id
	@GeneratedValue
	@Column(name = "folder_id")
	private Integer id; //GET folders/{folder_id}

	@ManyToOne
	@Column(name = "group_id")
	private Group group; //소유 그룹. 접근권한 확인 시 필요

	private Integer parent_id; // 상위 폴더 -> findAllByParentId(paren_id) -> 하위폴더 목록 가져옴
	//폴더 삭제 시 dfs, stack 써서 삭제

	//파일 이름, id 목록

}
