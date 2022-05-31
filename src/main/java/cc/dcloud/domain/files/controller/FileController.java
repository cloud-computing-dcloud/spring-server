package cc.dcloud.domain.files.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cc.dcloud.domain.File;
import cc.dcloud.domain.files.dto.FileDownloadDto;
import cc.dcloud.domain.files.dto.FileDownloadForm;
import cc.dcloud.domain.files.dto.FileDto;
import cc.dcloud.domain.files.service.FileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	/**
	 * 파일 업로드. 이걸 실행시키기 위해서는 folder 접근 권한을 거쳐야 함. 그래서 따로 권한 체킹을 하지 않았음.
	 * @param folderId
	 * @return
	 */
	@PostMapping("/folders/{folderId}/upload")
	public ResponseEntity<FileDto> uploadFile(@PathVariable Integer folderId,
		@RequestParam("file") MultipartFile fileData) {
		File file = File.builder()
			.fileName(fileData.getOriginalFilename())
			.fileSize(fileData.getSize())
			.folderId(folderId)
			.uploadTime(LocalDateTime.now())
			.build();
		Integer fileId = fileService.createFile(file);
		//s3 코드 넣으면 됨
		return ResponseEntity.ok(new FileDto(fileId, file.getFileName(), file.getUploadTime(), file.getFileSize()));
	}

	/**
	 * 파일 다운로드. s3 다운로드 Url을 리턴해주면 클라이언트에서 리다이렉트해서 다운로드하면 될듯.
	 * @param folderId
	 * @param form
	 * @return
	 */
	@PostMapping("/folders/{folderId}/download")
	public ResponseEntity<FileDownloadDto> downloadFile(@PathVariable Integer folderId,
		@RequestBody FileDownloadForm form) {
		Integer fileId = form.getFileId();
		StringBuilder sb = new StringBuilder();
		String s3Key = sb.append(folderId).append('/').append(fileId).toString();
		System.out.println("s3Key = " + s3Key);
		//s3로 다운로드 url 가져오고 return
		//String url =
		return ResponseEntity.ok(new FileDownloadDto("url"));
	}
}
