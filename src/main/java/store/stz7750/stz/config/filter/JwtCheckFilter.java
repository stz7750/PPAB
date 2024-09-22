package store.stz7750.stz.config.filter;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.io.SerialException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import store.stz7750.stz.jwtutil.JwtUtil;
import store.stz7750.stz.users.vo.UserVO;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

@Log4j2
public class JwtCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
    throws ServletException {
        /* 현재 path는 체크 합니다.
         false = jwt checked
         true = jwt not checked
        */
        String path = request.getRequestURI();

        if(path.startsWith("/api/")){
            return true;
        }else if(path.startsWith("/admin/api")) {
            return true;
        }
        log.info("현재 위치는...............:" + path);
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {


        String authHeaderStr = request.getHeader("Authorization");

        try {
            // Authorization 헤더가 없거나 Bearer로 시작하지 않으면 오류 처리
            if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
                handleLoginFailure(response, "JWT 토큰이 유효하지 않습니다.");
                return;
            }

            // JWT 토큰이 존재할 경우
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JwtUtil.validateToken(accessToken);  // 토큰 검증

            // 토큰에서 사용자 정보 추출
            String email = (String) claims.get("email");
            String role = (String) claims.get("role");
            String username = (String) claims.get("username");
            String name = (String) claims.get("name");

            // 사용자 정보로 UserVO 객체 생성
            UserVO userVO = new UserVO();
            userVO.setRole(role);
            userVO.setEmail(email);
            userVO.setName(name);
            userVO.setId(username);

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(name, userVO);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 필터 체인 계속 진행 (정상적으로 인증이 되었을 경우)
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 토큰이 유효하지 않거나 만료된 경우, 로그인 실패 처리
            log.error("JWT 토큰 오류: {}", e.getMessage());
            handleLoginFailure(response, "토큰 검증 실패: " + e.getMessage());
        }
    }

    private void handleLoginFailure(HttpServletResponse response, String message) throws IOException {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(Map.of("error", message));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 상태 코드
        response.setContentType("application/json");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(jsonStr);  // JSON 응답
        writer.close();
    }
    
}
