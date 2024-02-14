package store.stz7750.stz.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import store.stz7750.stz.config.handler.APILoginFailHandler;
import store.stz7750.stz.config.handler.APILoginSuccessHandler;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class CustomSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* httpSecurity 는 대부분의 설정을 담당하게 된다. (세부적인 보안 기능을 설정할 수 있는 API 제공) */
        log.info("---------------------Security config------------------");
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        }); // cors 허용.
        http.formLogin(config -> {
            config.loginPage("/api/login");
            config.successHandler(new APILoginSuccessHandler());
            config.failureHandler(new APILoginFailHandler());
        });  /* 기본 말고 커스텀 로그인 페이지로 접속해라.(Back , front 가 나눠진 프로젝트에선 api호출)
        니가 로그인에 성공했어? 그럼 핸들러가 동작할꺼야 근데 성공,실패로 나눠서 동작하지*/

        http.sessionManagement(httpSecuritySessionManagementConfigurer->{
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }); // 세션 만들지마라
        
        http.csrf(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        /* rest api 는 csrf 공격에 안전하고 매번 인증을 받아야하는 상황이라서 disable 처리를 해준다. */
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
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
