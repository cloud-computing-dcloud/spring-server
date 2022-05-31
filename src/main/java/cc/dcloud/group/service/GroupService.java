package cc.dcloud.group.service;

import cc.dcloud.domain.Group;
import cc.dcloud.domain.GroupType;
import cc.dcloud.group.dto.GroupDTO;
import cc.dcloud.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Group create(Integer memberId, String name, GroupType groupType) {
        Group group = Group.create(memberId, name, groupType);

        groupRepository.save(group);
        return group;
    }

    @Transactional
    public void join(Integer groupId, Integer memberId){
        // 로그인 인증 및 권한

        Group group = groupRepository.findById(groupId);
        //group.join(memberId); // 이거 어떄?
    }

    // 회원이 참가한 그룹 리스트 return
//    public List<Group> findAllByMember(Integer memberId){ // 메서드 이름 바꾸자
//        // 로그인 인증 및 권한
//
//        // 회원 id가 일치하는 MemberGroup의 엔티티를 찾아 groupId를 return.
//        // 궁금한 것 : M:N 매핑을 위해 만든 매핑 엔티티도 repository, service 만드는게 좋은 패턴인가?
//    }
}