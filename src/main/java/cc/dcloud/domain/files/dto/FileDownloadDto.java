package cc.dcloud.domain.files.dto;

import lombok.Getter;

@Getter
public class FileDownloadDto {
	private String downloadUrl;

	public FileDownloadDto(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
