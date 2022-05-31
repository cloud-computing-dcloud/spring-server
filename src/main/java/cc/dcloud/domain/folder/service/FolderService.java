package cc.dcloud.domain.folder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.Folder;
import cc.dcloud.domain.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FolderService {
	
	private final FolderRepository folderRepository;

	@Transactional
	public Integer createFolder(Folder folder) {
		return folderRepository.save(folder);
	}

	public List<Folder> showAllSubFolders(Integer folderId) {
		Folder folder = folderRepository.findById(folderId);
		return folderRepository.findAllByParentFolderId(folder);
	}

	public Folder getFolderById(Integer folderId) {
		return folderRepository.findById(folderId);
	}
}
