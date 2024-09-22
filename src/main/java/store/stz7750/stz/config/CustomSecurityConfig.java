package store.stz7750.stz.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import store.stz7750.stz.config.filter.JwtCheckFilter;
import store.stz7750.stz.config.handler.APILoginFailHandler;
import store.stz7750.stz.config.handler.APILoginSuccessHandler;
import store.stz7750.stz.config.handler.CustomAccessDeniedHandler;
import store.stz7750.stz.users.service.UserService;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {

    private final UserService userService;

    private final stzUserDetailsService stzUserDetailsService;

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* httpSecurity 는 대부분의 설정을 담당하게 된다. (세부적인 보안 기능을 설정할 수 있는 API 제공) */
        log.info("---------------------Security config------------------");
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        }); // cors 허용.
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }); // 세션 만들지마라
        http.csrf(httpSecuritycsrfConfigurer -> httpSecuritycsrfConfigurer.disable());
        http.formLogin(config -> {
            config.loginPage("/api/login");
            config.successHandler(new APILoginSuccessHandler(userService));
            config.failureHandler(new APILoginFailHandler());
        });  /* 기본 말고 커스텀 로그인 페이지로 접속해라.(Back , front 가 나눠진 프로젝트에선 api호출)
        니가 로그인에 성공했어? 그럼 핸들러가 동작할꺼야 근데 성공,실패로 나눠서 동작하지*/
        http.addFilterBefore(new JwtCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        /* Custom filter를 추가해준다. jwt 검증에 필요 */
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomAccessDeniedHandler());
        });
        return http.build();
    }
/* 
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    } */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();


        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8888", "http://localhost:1208"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // 인증 관련 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // boardUserDetailsService를 사용하여 사용자 인증 처리
        auth.userDetailsService(stzUserDetailsService);
    }

}
