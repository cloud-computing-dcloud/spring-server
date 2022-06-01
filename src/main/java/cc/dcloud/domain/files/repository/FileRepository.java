package cc.dcloud.domain.files.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import cc.dcloud.domain.File;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FileRepository {

	private final EntityManager em;

	public Integer save(File file) {
		em.persist(file);
		return file.getId();
	}

	public File findById(Integer id) throws IllegalAccessException {
		File file = em.find(File.class, id);
		if(file==null){
			throw new IllegalAccessException("존재하지 않는 파일입니다.");
		}
		return file;
	}

	public List<File> findAllFilesByFolderId(Integer folderId) {
		return em.createQuery("select f from File  f where f.folderId = :folderId", File.class)
			.setParameter("folderId", folderId)
			.getResultList();
	}

	public void delete(File file){
		em.remove(file);
	}
}
