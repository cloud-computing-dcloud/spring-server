package cc.dcloud.domain.login.service;

import cc.dcloud.domain.Member;
import cc.dcloud.domain.login.pojo.CacheKey;
import cc.dcloud.domain.login.pojo.CustomUserDetails;
import cc.dcloud.domain.member.repository.MemberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Cacheable - 토큰을 줄 때마다 데이터베이스를 거치는 것을 줄이기 위해 설정
     */
    @Override
    @Cacheable(value = CacheKey.USER, key = "#username", unless = "#result == null")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsernameWithAuthority(username)
                .orElseThrow(() -> new NoSuchElementException("없는 회원"));
        return CustomUserDetails.of(member);
    }
}
