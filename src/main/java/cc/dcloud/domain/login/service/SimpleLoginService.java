package cc.dcloud.domain.login.service;

import cc.dcloud.domain.folder.service.FolderService;
import cc.dcloud.domain.group.Group;
import cc.dcloud.domain.GroupType;
import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.memberGroup.MemberGroup;
import cc.dcloud.domain.login.dto.LoginDto;
import cc.dcloud.domain.login.dto.MemberDto;
import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.domain.login.dto.TokenDto;
import cc.dcloud.exception.NotFoundException;
import cc.dcloud.exception.NotMatchNameException;
import cc.dcloud.domain.login.pojo.CacheKey;
import cc.dcloud.domain.login.pojo.LogoutAccessToken;
import cc.dcloud.domain.login.pojo.RefreshToken;
import cc.dcloud.domain.login.repository.LogoutAccessTokenRedisRepository;
import cc.dcloud.domain.login.repository.RefreshTokenRedisRepository;
import cc.dcloud.domain.login.util.JwtExpirationEnums;
import cc.dcloud.domain.login.util.JwtTokenUtil;
import cc.dcloud.domain.member.service.MemberService;
import cc.dcloud.domain.group.service.GroupService;
import cc.dcloud.domain.memberGroup.service.MemberGroupService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cc.dcloud.domain.login.util.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
@Transactional(readOnly = true)
public class SimpleLoginService implements LoginService{

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final MemberService memberService;
    private final GroupService groupService;
    private final MemberGroupService memberGroupService;
    private final FolderService folderService;

    public SimpleLoginService(PasswordEncoder passwordEncoder, RefreshTokenRedisRepository refreshTokenRedisRepository,
        LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository, JwtTokenUtil jwtTokenUtil,
        MemberService memberService, GroupService groupService, MemberGroupService memberGroupService,
        FolderService folderService) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.logoutAccessTokenRedisRepository = logoutAccessTokenRedisRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.groupService = groupService;
        this.memberGroupService = memberGroupService;
        this.folderService = folderService;
    }

    @Override
    @Transactional
    public void signUp(SignUpDto signUpDto) {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Member member = memberService.signUp(signUpDto);
        Group group = groupService.create(signUpDto.getUsername(), GroupType.PRIVATE);
        folderService.createRootFolder(group);
        memberGroupService.create(member.getId(), group.getId());
    }

    @Override
    @Transactional
    public void signUpAdmin(SignUpDto signUpDto) {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Member member = memberService.signUpAdmin(signUpDto);
        Group group = groupService.create(signUpDto.getUsername(), GroupType.PRIVATE);
        memberGroupService.create(member.getId(), group.getId());
    }

    @Override
    @Transactional
    public TokenDto login(LoginDto loginDto) {

        Member member = memberService.getByUsername(loginDto.getUsername());

        member.checkPassword(passwordEncoder, loginDto.getPassword());
        String username = member.getUsername();
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        RefreshToken refreshToken = saveRefreshToken(username);
        return TokenDto.of(accessToken, refreshToken.getRefreshToken());

    }

    @Override
    public MemberDto getMemberInfo(String username) {
        Member member = memberService.getByUsername(username);

        List<MemberGroup> memberGroupList = memberGroupService.getByMemberId(member.getId());

        if(!username.equals(getCurrentUsername())) {
            throw new NotMatchNameException();
        }

        return MemberDto.builder()
                .username(username)
                .memberGroupList(memberGroupList)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheKey.USER, key = "#username")
    public void logout(TokenDto tokenDto, String username) {
        String accessToken = resolveToken(tokenDto.getAccessToken());

        long remainMilliSeconds = jwtTokenUtil.getRemainMilliSeconds(accessToken);
        refreshTokenRedisRepository.deleteById(username);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken, username, remainMilliSeconds));
    }

    @Override
    @Transactional
    public TokenDto reissue(String refreshToken) {
        refreshToken = resolveToken(refreshToken);
        String username = getCurrentUsername();
        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(username)
                .orElseThrow(NotFoundException::new);

        redisRefreshToken.checkToken(refreshToken);

        return reissueRefreshToken(refreshToken, username);

    }

    private RefreshToken saveRefreshToken(String username) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(
                username,
                jwtTokenUtil.generateAccessToken(username),
                REFRESH_TOKEN_EXPIRATION_TIME.getValue()
        ));
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }

    private String resolveToken(String accessToken) {
        return accessToken.substring(7);
    }


    private TokenDto reissueRefreshToken(String refreshToken, String username) {
        if(lessThanReissueExpirationTimesLeft(refreshToken)) {
            String accessToken = jwtTokenUtil.generateAccessToken(username);
            return TokenDto.of(accessToken, saveRefreshToken(username).getRefreshToken());
        }
        return TokenDto.of(jwtTokenUtil.generateAccessToken(username), refreshToken);

    }

    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtTokenUtil.getRemainMilliSeconds(refreshToken)
                < JwtExpirationEnums.REISSUE_EXPIRATION_TIME.getValue();
    }
}
