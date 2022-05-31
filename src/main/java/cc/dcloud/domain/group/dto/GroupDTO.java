package cc.dcloud.domain.group.dto;

import cc.dcloud.domain.Folder;
import cc.dcloud.domain.GroupType;
import cc.dcloud.domain.memberGroup.MemberGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroupDTO {

    private Integer id;
    private String name;
    private List<MemberGroup> memberGroupList;
    private GroupType groupType;
    private Integer rootFolderId;
    private List<Folder> folderList;

}
