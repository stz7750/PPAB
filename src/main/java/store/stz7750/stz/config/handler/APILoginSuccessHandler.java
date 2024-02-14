package store.stz7750.stz.config.handler;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import store.stz7750.stz.config.SecurityUser;
import store.stz7750.stz.jwtutil.JwtUtil;
import store.stz7750.stz.users.vo.UserVO;

@Log4j2 
public class APILoginSuccessHandler implements AuthenticationSuccessHandler{
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
            

            log.info("-----------동작은 하냐?--------");
            log.info(authentication);
            log.info("------security 다시는 쓰고싶지 않다 -------");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            UserVO userVO = securityUser.getUser();

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userVO.getId()); 
            claims.put("name", userVO.getName()); 
            claims.put("email", userVO.getEmail()); 
            claims.put("role", userVO.getRole().toString());
            String accessToken = JwtUtil.generateToken(claims, 10);
            String refreshToken = JwtUtil.generateToken(claims,60*24);
            claims.put("accessToken",accessToken);
            claims.put("refreshToken", refreshToken);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(claims);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(jsonStr);
            out.flush();

        
    }

}
