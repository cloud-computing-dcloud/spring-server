package cc.dcloud.domain.files.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDto {
	private Integer id;
	private String name;
	private LocalDateTime uploadTime;
	private Long fileSize;
}
