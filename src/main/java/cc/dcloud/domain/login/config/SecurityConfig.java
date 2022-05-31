package cc.dcloud.domain.login.config;

import cc.dcloud.domain.login.service.CustomUserDetailService;
import cc.dcloud.domain.login.util.JwtAuthenticationFilter;
import cc.dcloud.domain.login.util.JwtEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 시큐리티는 각종 권한 인증 등등 보안 관련된 것을 체크 하기 위해 여러 필터 존재
     * JWT 기반으로 구현하기 위해 JwtAuthenticationFilter 클래스 구현
     * 필터 과정중 에러가 발생한 겨우 JwtEntryPoint에서 처리하도록 구현
     */
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 유저 정보를 가져오기 위한 클래스
    private final CustomUserDetailService customUserDetailService;



    public SecurityConfig(JwtEntryPoint jwtEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailService customUserDetailService) {
        this.jwtEntryPoint = jwtEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 비밀번호 암호화 클래스, 회원가입시 입력한 비밀번호를 BCrypt 해쉬를 통해 암호화
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 시큐리티를 설정 시 h2 데이터 콘솔에 접속할 수 없는 상황 해결
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**", "/favicon.ico");
    }

    /**
     *  antMatchers + permitAll -> url은 권한에 제한없이 요청 가능
     *  JWT기반이기 때문에 logout은 disable
     *  시큐리티는 기본 로그인 / 로그아웃시 세션을 통해 유저 정보들을 저장한다.
     *  Redis를 사용하기 때문에 상태를 저장하지 않는 Stateless로 설정
     *  JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 추가하겠다는 의미
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable()
                .authorizeRequests() // 5
                .antMatchers("/", "/signUp/**", "/login").permitAll()
                .antMatchers("/logout").authenticated()
                .anyRequest().hasRole("USER")

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)

                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

}
