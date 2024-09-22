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
    
        
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info("--------------------------------------");
        log.info("--------------------------------------");

        String authHeaderStr = request.getHeader("Authorization");
        try {
            if(authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")){
                String accessToken = authHeaderStr.substring(7);
                Map<String, Object> claims = JwtUtil.validateToken(accessToken);
                String email = (String) claims.get("email");
                String role = (String) claims.get("role");
                String username = (String) claims.get("username");
                String name = (String) claims.get("name");

                UserVO userVO = new UserVO();
                userVO.setRole(role);
                userVO.setEmail(email);
                userVO.setName(name);
                userVO.setId(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name, userVO);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                /* 다음 목적지로 가게해주세요. */
                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            /* 토큰이 만료 됐을 때. */
            log.error(e.getMessage());

            Gson gson = new Gson();
            String messg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(messg);
            out.close();
            
        }
    }
    
}
