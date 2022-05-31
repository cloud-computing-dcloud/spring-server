package cc.dcloud.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class File {

	@Id
	@GeneratedValue
	@Column(name = "file_id")
	private Integer id;

	private String fileName;

	private Integer folderId; //속한 폴더 id. 삭제 시 findAll(folderId)후 dfs로

	private LocalDateTime uploadTime; //할지 안할지 모르겠음

	private Integer fileSize; //파일 용량 (Byte)
}