package cc.dcloud.domain.files.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.dcloud.domain.files.File;
import cc.dcloud.domain.files.repository.FileRepository;
import cc.dcloud.domain.folder.repository.FolderRepository;
import cc.dcloud.exception.AlreadyExistException;
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

	public File findById(Integer id) {
		return fileRepository.findById(id);
	}

	public List<File> showAllSubFiles(Integer folderId) {
		return fileRepository.findAllFilesByFolderId(folderId);
	}

	@Transactional
	public void deleteFile(File file) {
		fileRepository.delete(file);
	}

	public void validateFileAlreadyExist(Integer folderId, String fileName) throws AlreadyExistException {
		if (fileRepository.findAllFilesByFolderId(folderId)
			.stream().anyMatch(file -> Objects.equals(file.getFileName(), fileName)))
			throw new AlreadyExistException("이미 존재하는 파일명입니다.");
	}
}
