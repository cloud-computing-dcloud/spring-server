package cc.dcloud.domain.aws.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
@PropertySource("classpath:aws.properties")
public class AwsS3Service {

	private static final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public AwsS3Service(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public List<String> uploadFile(Integer folderId, List<MultipartFile> multipartFileList) {

		List<String> fileNameList = new ArrayList<>();

		multipartFileList.forEach(file -> {
			// String fileName = createFileName(file.getOriginalFilename());
			String fileName = folderId + "/" + file.getOriginalFilename();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			// file.getInputStream = 파일로부터 바이트 단위의 입력을 받음
			// -> IOException 발생 가능있다. => try - catch
			try (InputStream inputStream = file.getInputStream()) {

				amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				logger.error("파일 업로드 실패!!!! -> {}", e.getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
			}

			fileNameList.add(fileName);
		});
		return fileNameList;
	}

	public void deleteFile(String fileName) {
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
	}

	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			logger.error("잘못된 형식 파일 !!!! -> {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 (" + fileName + ") 입니다. ");
		}
	}

	public String downloadFile(String fileName) {
		return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucket, fileName);
	}
}
