package cc.dcloud.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;

@Entity
@Getter
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Integer id;

	private String username; // login id

	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "member")
	private List<MemberGroup> memberGroupList = new ArrayList<>();

}
