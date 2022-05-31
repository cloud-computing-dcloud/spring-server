package cc.dcloud.group.repository;

import cc.dcloud.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupRepository {
    private final EntityManager em;

    public void save(Group group){
        em.persist(group);
    }

    public Group findById(Integer id){
        return em.find(Group.class, id);
    }
}
