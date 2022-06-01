package cc.dcloud.domain.aws.controller;

import cc.dcloud.domain.aws.service.AwsS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/s3")
@CrossOrigin("*")
public class AmazonS3Controller {

    private final AwsS3Service awsS3Service;

    public AmazonS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/file")
    public ResponseEntity<List<String>> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        List<String> fileNameList = awsS3Service.uploadFile(multipartFile);
        return ResponseEntity.ok(fileNameList);
    }

    @GetMapping("/file")
    public ResponseEntity<String> downloadFile(@RequestParam String fileName) {
       String url = awsS3Service.downloadFile(fileName);
       return ResponseEntity.ok(url);
    }


    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.noContent().build();
    }

}
