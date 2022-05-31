package cc.dcloud.domain.group.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CreateGroupDTO {
    Integer memberId; // jwt 에서 id가져오는 방식에 따라서 없어질수도있음
    String name;

    public CreateGroupDTO(){}

    public CreateGroupDTO(Integer memberId, String name){
        this.memberId = memberId;
        this.name = name;
    }
}