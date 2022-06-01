package cc.dcloud.domain.login.util;

import cc.dcloud.exception.NotMatchNameException;
import cc.dcloud.exception.NotMatchTokenException;
import cc.dcloud.domain.login.repository.LogoutAccessTokenRedisRepository;
import cc.dcloud.domain.login.service.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailService customUserDetailService, LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailService = customUserDetailService;
        this.logoutAccessTokenRedisRepository = logoutAccessTokenRedisRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getToken(request);
        if(accessToken != null) {
            checkLogout(accessToken);
            String username = jwtTokenUtil.getUsername(accessToken);
            if(username != null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                equalsUsernameFromTokenAndUserDetails(userDetails.getUsername(), username);
                validateAccessToken(accessToken, userDetails);
                processSecurity(request, userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * JWT 를 'Bearer ' 를 제외하여 가져온다.
     * jwt를 프론트에서 주지 않았을 경우 null 반환
     */
    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    /**
     * 이 토큰이 로그아웃된 토큰인지 검증
     */
    private void checkLogout(String accessToken) {
        if(logoutAccessTokenRedisRepository.existsById(accessToken)) {
            throw new IllegalArgumentException("이미 로그아웃 된 회원입니다");
        }
    }

    private void equalsUsernameFromTokenAndUserDetails(String userDetailsUsername, String tokenUsername) {
        if(!userDetailsUsername.equals(tokenUsername)) {
            throw new NotMatchNameException("username이 토큰과 맞지 않습니다.");
        }
    }

    private void validateAccessToken(String accessToken, UserDetails userDetails) {
        if(!jwtTokenUtil.validateToken(accessToken, userDetails)) {
            throw new NotMatchTokenException("토큰 검증에 실패했습니다");
        }
    }

    /**
     * 해당 정보를 SecurityContext에 추가
     */
    private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }





}
