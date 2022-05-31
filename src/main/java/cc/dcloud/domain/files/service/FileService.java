package cc.dcloud.domain.files.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.File;
import cc.dcloud.domain.files.repository.FileRepository;
import cc.dcloud.domain.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

	private final FileRepository fileRepository;
	private final FolderRepository folderRepository;

	@Transactional
	public Integer createFile(File file) {
		return fileRepository.save(file);
	}

	public List<File> showAllSubFiles(Integer folderId) {
		return fileRepository.findAllFilesByFolderId(folderId);
	}
}
