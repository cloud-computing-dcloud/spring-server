package cc.dcloud.domain.folder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cc.dcloud.domain.File;
import cc.dcloud.domain.Folder;
import cc.dcloud.domain.files.service.FileService;
import cc.dcloud.domain.folder.dto.ContentDto;
import cc.dcloud.domain.folder.dto.FolderCreateForm;
import cc.dcloud.domain.folder.dto.FolderDto;
import cc.dcloud.domain.folder.service.FolderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FolderController {

	private final FolderService folderService;
	private final FileService fileService;

	/**
	 * 폴더 내용물 (폴더 목록, 파일 목록) 출력
	 * @param folderId
	 * @return ContentDto (폴더 목록, 파일 목록)
	 */
	@GetMapping("folders/{folderId}")
	public ResponseEntity<ContentDto> getAllContents(@PathVariable Integer folderId) {
		List<Folder> folders = folderService.showAllSubFolders(folderId);
		List<File> files = fileService.showAllSubFiles(folderId);
		ContentDto content = new ContentDto();
		content.createContentDto(folders, files);
		return ResponseEntity.ok(content);
	}

	/**
	 * 현재 폴더 아래에 하위 폴더 생성
	 * @param folderId name
	 * @return FolderDto
	 */
	@PostMapping("folders/{folderId}/create")
	public ResponseEntity<FolderDto> createSubFolder(@PathVariable Integer folderId, @RequestBody FolderCreateForm form) {
		String name = form.getName();
		System.out.println(name);
		Folder nowFolder = folderService.getFolderById(folderId);
		Integer id = folderService.createSubFolder(
			new Folder(name, nowFolder.getGroup(), nowFolder.getId())
		);
		return ResponseEntity.ok(new FolderDto(id, name));
	}

}
