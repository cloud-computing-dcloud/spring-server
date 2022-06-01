package cc.dcloud.domain.folder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import cc.dcloud.domain.Folder;
import cc.dcloud.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FolderRepository {

	private final EntityManager em;

	public Integer save(Folder folder){
		em.persist(folder);
		return folder.getId();
	}

	public void delete(Folder folder) {
		em.remove(folder);
	}

	public Folder findById(Integer id) throws NotFoundException {

		Folder folder = em.find(Folder.class, id);
		if (folder == null) {
			throw new NotFoundException("존재하지 않는 폴더 입니다.");
		}
		return folder;
	}

	public List<Folder> findAllByParentFolderId(Integer parentId) {
		return em.createQuery("select f from Folder f where f.parentId = :parentId", Folder.class)
			.setParameter("parentId", parentId)
			.getResultList();
	}
}
