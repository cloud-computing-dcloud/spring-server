package cc.dcloud.domain.folder.dto;

import java.util.ArrayList;
import java.util.List;

import cc.dcloud.domain.File;
import cc.dcloud.domain.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ContentDto {


	private List<FileDto> files;
	private List<FolderDto> folders;

	public void createContentDto(List<Folder> folderList, List<File> fileList) {
		folders = new ArrayList<>();
		for (Folder folder : folderList) {
			folders.add(new FolderDto(folder.getId(), folder.getName()));
		}
		files = new ArrayList<>();
		for (File file : fileList) {
			files.add(new FileDto(file.getId(), file.getFileName()));
		}
	}

}