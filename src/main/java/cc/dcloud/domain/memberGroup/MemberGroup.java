package cc.dcloud.domain.memberGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "member_group")
@Getter
@Builder
public class MemberGroup {

	@Id
	@GeneratedValue
	@Column(name = "member_group_id")
	private Integer id;

	//	@ManyToOne(fetch = FetchType.LAZY)
	//	@JoinColumn(name = "member_id")
	//	private Member member;
	//
	//	@ManyToOne(fetch = FetchType.LAZY)
	//	@JoinColumn(name = "group_id")
	//	private Group group;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "group_id")
	private Integer groupId;

	public MemberGroup(Integer id, Integer memberId, Integer groupId) {
		this.id = id;
		this.memberId = memberId;
		this.groupId = groupId;
	}

	//===생성 메서드===//
	protected MemberGroup() {
	}

	public static MemberGroup create(Integer memberId, Integer groupId) {
		return MemberGroup.builder()
			.memberId(memberId)
			.groupId(groupId)
			.build();
	}
}