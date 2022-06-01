package cc.dcloud.domain.login.util;

import static cc.dcloud.domain.login.util.JwtExpirationEnums.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	/**
	 * 토큰 추출 메서드
	 * payload 값 가져온다.
	 */
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey(SECRET_KEY))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public String getUsername(String token) {
		return extractAllClaims(token).get("username", String.class);
	}

	private Key getSigningKey(String secretKey) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Boolean isTokenExpired(String token) {
		Date expiration = extractAllClaims(token).getExpiration();
		return expiration.before(new Date());
	}

	public String generateAccessToken(String username) {
		return doGenerateToken(username, ACCESS_TOKEN_EXPIRATION_TIME.getValue());
	}

	public String generateRefreshToken(String username) {
		return doGenerateToken(username, REFRESH_TOKEN_EXPIRATION_TIME.getValue());
	}

	/**
	 * 토큰 생성 메서드
	 * JWT는 header.payload.signature로 구성
	 * username, 발급날짜, 만료기간을 payload 에 넣어주고
	 * yml에서 설정한 secretKey 로 서명후 HS256 알고리즘으로 암호화
	 */
	private String doGenerateToken(String username, long expireTime) {
		Claims claims = Jwts.claims();
		claims.put("username", username);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expireTime))
			.signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
			.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public long getRemainMilliSeconds(String token) {
		Date expiration = extractAllClaims(token).getExpiration();
		Date now = new Date();
		return expiration.getTime() - now.getTime();
	}

}
