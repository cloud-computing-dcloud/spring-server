package cc.dcloud.domain.folder.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cc.dcloud.domain.File;
import cc.dcloud.domain.Folder;
import cc.dcloud.domain.files.service.FileService;
import cc.dcloud.domain.folder.dto.ContentDto;
import cc.dcloud.domain.folder.dto.FolderCreateForm;
import cc.dcloud.domain.folder.dto.FolderDto;
import cc.dcloud.domain.folder.service.FolderService;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.login.util.JwtTokenUtil;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.member.service.MemberService;
import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.memberGroup.service.MemberGroupService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class FolderController {

	private final FolderService folderService;
	private final FileService fileService;
	private final JwtTokenUtil jwt;
	private final MemberService memberService;
	private final MemberGroupService memberGroupService;

	/**
	 * 폴더 내용물 (폴더 목록, 파일 목록) 출력
	 * @param folderId
	 * @return ContentDto (폴더 목록, 파일 목록)
	 */
	@GetMapping("folders/{folderId}")
	public ResponseEntity<ContentDto> getAllContents(@PathVariable Integer folderId,
		@RequestHeader String authorization) {
		Member member = getMemberByToken(authorization);
		boolean exist = validateMember(folderId, member);
		if (!exist) {
			return ResponseEntity.notFound().build();
		}
		List<Folder> folders = folderService.showAllSubFolders(folderId);
		List<File> files = fileService.showAllSubFiles(folderId);
		ContentDto content = new ContentDto();
		content.createContentDto(folders, files);
		return ResponseEntity.ok(content);
	}

	private boolean validateMember(Integer folderId, Member member) {
		Integer id = member.getId();
		Folder folder = folderService.getFolderById(folderId);
		Group group = folder.getGroup();
		List<MemberGroup> memberGroups = memberGroupService.getByMemberId(id);
		boolean exist = memberGroups.stream().anyMatch(v ->
			Objects.equals(v.getGroupId(), group.getId())
		);
		return exist;
	}

	private Member getMemberByToken(String authorization) {
		String token = authorization.substring(7);
		return memberService.getByUsername(jwt.getUsername(token));
	}

	/**
	 * 현재 폴더 아래에 하위 폴더 생성
	 * @param folderId name
	 * @return FolderDto
	 */
	@PostMapping("folders/{folderId}/create")
	public ResponseEntity<FolderDto> createSubFolder(@PathVariable Integer folderId,
		@RequestBody FolderCreateForm form) {
		String name = form.getName();
		System.out.println(name);
		Folder nowFolder = folderService.getFolderById(folderId);
		Integer id = folderService.createSubFolder(
			new Folder(name, nowFolder.getGroup(), nowFolder.getId())
		);
		return ResponseEntity.ok(new FolderDto(id, name));
	}

}
