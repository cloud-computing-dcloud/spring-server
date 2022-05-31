package cc.dcloud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "member_group")
@Getter
public class MemberGroup {

	@Id
	@GeneratedValue
	@Column(name = "member_group_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	//===생성 메서드===//
	protected MemberGroup(){}
	public static MemberGroup create(Member member, Group group){
		MemberGroup memberGroup = new MemberGroup();

		memberGroup.member = member;
		memberGroup.group = group;
		return memberGroup;
	}
}