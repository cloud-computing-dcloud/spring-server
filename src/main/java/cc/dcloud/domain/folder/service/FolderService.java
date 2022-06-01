package cc.dcloud.domain.folder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.File;
import cc.dcloud.domain.Folder;
import cc.dcloud.domain.files.repository.FileRepository;
import cc.dcloud.domain.folder.repository.FolderRepository;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.member.Member;
import cc.dcloud.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FolderService {
	
	private final FolderRepository folderRepository;
	private final FileRepository fileRepository;

	@Transactional
	public Integer createSubFolder(Folder folder) {
		return folderRepository.save(folder);
	}

	@Transactional
	public Integer createRootFolder(Group group){
		Folder folder = new Folder("root", group, null);
		Integer id = folderRepository.save(folder);
		group.setRootFolderId(id);
		folder.setGroup(group);
		return id;
	}

	public List<Folder> showAllSubFolders(Integer folderId) {
		return folderRepository.findAllByParentFolderId(folderId);
	}

	public Folder getFolderById(Integer folderId) throws NotFoundException {
		return folderRepository.findById(folderId);
	}

	@Transactional
	public void deleteFolderDfs(Integer folderId) {
		Folder folder = folderRepository.findById(folderId);
		//하위 폴더, 파일 모두 삭제해야함
		List<Folder> subFolders = showAllSubFolders(folderId);
		for (Folder subFolder : subFolders) {
			deleteFolderDfs(subFolder.getId());
		}
		//파일 삭제
		List<File> files = fileRepository.findAllFilesByFolderId(folderId);
		for (File file : files) {
			fileRepository.delete(file);
			//s3 파일 삭제
			String s3Key = folderId + "/" + file.getId();
		}
		folderRepository.delete(folder);
	}
}
