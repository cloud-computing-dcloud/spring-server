package cc.dcloud.domain.folder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import cc.dcloud.domain.Folder;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FolderRepository {

	private final EntityManager em;

	public Integer save(Folder folder){
		em.persist(folder);
		return folder.getId();
	}

	public Folder findById(Integer id) {
		return em.find(Folder.class, id);
	}

	public List<Folder> findAllByParentFolderId(Folder folder) {
		Integer parentId = folder.getParentId();
		return em.createQuery("select f from Folder  f where f.parentId = :parentId", Folder.class)
			.setParameter("parentId", parentId)
			.getResultList();
	}
}
