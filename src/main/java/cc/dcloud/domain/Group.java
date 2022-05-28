package cc.dcloud.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue
	@Column(name = "group_id")
	private Integer id;

	@Column(name = "group_name")
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	private List<MemberGroup> memberGroupList = new ArrayList<>();

	@Column(name = "group_type")
	@Enumerated(EnumType.STRING)
	private GroupType groupType;

	@Column(name = "root_folder_id")
	private Integer rootFolderId; //index page

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
	@Column(name = "folder_id")
	private List<Folder> folderList = new ArrayList<>();
	// 있으면 개발은 편해짐. 성능 안좋아질수있다. 우리 서비스 그렇게 크게 안하니까 일단 개발이렇게 하자
	// 없으면 rootFolderid통해서 dfs돌려서 하나씩삭제해줘야함
}
