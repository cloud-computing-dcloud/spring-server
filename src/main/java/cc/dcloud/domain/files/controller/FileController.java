package cc.dcloud.domain.files.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import cc.dcloud.domain.File;
import cc.dcloud.domain.aws.service.AwsS3Service;
import cc.dcloud.domain.files.dto.FileDeleteForm;
import cc.dcloud.domain.files.dto.FileDownloadDto;
import cc.dcloud.domain.files.dto.FileDownloadForm;
import cc.dcloud.domain.files.dto.FileDto;
import cc.dcloud.domain.files.service.FileService;
import cc.dcloud.exception.AlreadyExistException;
import cc.dcloud.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileController {

	private final FileService fileService;
	private final AwsS3Service awsS3Service;

	/**
	 * 파일 업로드. 이걸 실행시키기 위해서는 folder 접근 권한을 거쳐야 함. 그래서 따로 권한 체킹을 하지 않았음.
	 * @param folderId
	 * @return
	 */
	@PostMapping("/folders/{folderId}/upload")
	public ResponseEntity<FileDto> uploadFile(@PathVariable Integer folderId,
		@RequestParam("file") MultipartFile fileData) {
		try {
			fileService.validateFileAlreadyExist(folderId, fileData.getOriginalFilename());
		} catch (AlreadyExistException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
			return new ResponseEntity<>(
				null, new HttpHeaders(), HttpStatus.CONFLICT);
		}
		File file = File.builder()
			.fileName(fileData.getOriginalFilename())
			.fileSize(fileData.getSize())
			.folderId(folderId)
			.uploadTime(LocalDateTime.now())
			.build();
		Integer fileId = fileService.createFile(file);
		//s3 코드 넣으면 됨
		List<MultipartFile> multipartFileList = new ArrayList<>();
		multipartFileList.add(fileData);
		List<String> uuid = awsS3Service.uploadFile(folderId, multipartFileList);
		uuid.forEach(System.out::println);

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
		//validate 필요 (존재하는 파일인지)
		try {
			Integer fileId = form.getFileId();
			File file = fileService.findById(fileId);
			String s3Key = folderId + "/" + file.getFileName();
			String url = awsS3Service.downloadFile(s3Key);
			return ResponseEntity.ok(new FileDownloadDto(url));
		} catch (NotFoundException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
			return new ResponseEntity<>(
				null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 파일 삭제.
	 * @param folderId
	 * @param form { "fileId" : Integer }
	 * @return
	 */
	@DeleteMapping("/folders/{folderId}/delete")
	public String deleteFile(@PathVariable Integer folderId,
		@RequestBody FileDeleteForm form) {
		try {
			Integer fileId = form.getFileId();
			File file = fileService.findById(fileId);
			fileService.deleteFile(file);

			//s3 삭제 코드 추가 필요
			String s3Key = folderId + "/" + file.getFileName();
			awsS3Service.deleteFile(s3Key);
			return "success";
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "File Not Found", ex);
		}
	}
}
