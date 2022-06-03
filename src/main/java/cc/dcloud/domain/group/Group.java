package cc.dcloud.domain.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "drivers")
@Getter
@Builder
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "group_id")
	private Integer id;

	@Column(name = "group_name")
	private String name;

	// 단방향 연관관계로 수정. 강호까지 OK하면 주석 삭제
	//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	//	private List<MemberGroup> memberGroupList = new ArrayList<>();

	@Column(name = "group_type")
	@Enumerated(EnumType.STRING)
	private GroupType groupType;

	@Column(name = "root_folder_id")
	@Builder.Default
	private Integer rootFolderId = 1; //index page

	//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
	//	@JoinColumn(name = "folder_id")
	//	@Builder.Default
	//	private List<Folder> folderList = new ArrayList<>();
	// 있으면 개발은 편해짐. 성능 안좋아질수있다. 우리 서비스 그렇게 크게 안하니까 일단 개발이렇게 하자
	// 없으면 rootFolderid통해서 dfs돌려서 하나씩삭제해줘야함

	//===생성 메서드===//
	protected Group() {
	}

	public Group(Integer id, String name, GroupType groupType, Integer rootFolderId) {
		this.id = id;
		this.name = name;
		this.groupType = groupType;
		this.rootFolderId = rootFolderId;
	}

	public static Group create(String name, GroupType groupType) { // 파라미터 추가(folder)
		return Group.builder()
			.name(name)
			.groupType(groupType)
			.build();
		// folder 생성 후 return되는 folderId를 group.rootFolderId에 저장
		// cascade.all을 그대로 쓸거면 folder를 추가하는 연관관계 편의 메서드를 Group에 정의하고,
		// root folder id

	}

	public void setRootFolderId(Integer rootFolderId) {
		this.rootFolderId = rootFolderId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Group{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", groupType=").append(groupType);
		sb.append(", rootFolderId=").append(rootFolderId);
		//		sb.append(", folderList=").append(folderList);
		sb.append('}');
		return sb.toString();
	}
}
