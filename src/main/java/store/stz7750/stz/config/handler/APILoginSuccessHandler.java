package store.stz7750.stz.config.handler;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.Authentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import store.stz7750.stz.users.vo.UserVO;

@Log4j2 
public class APILoginSuccessHandler implements AuthenticationSuccessHandler{
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    
                                            log.info("-----------동작은 하냐?--------");
                                            log.info(authentication);
                                            log.info("------security 다시는 쓰고싶지 않다 -------");

    UserVO userVO = (UserVO) authentication.getPrincipal(); //인증 받은 사용자 정보를 userVO 타입으로 변환.
    Map<String, Object> claims = userVO.getClaims();

    claims.put("accessToken","");
    claims.put("refreshToken", "");

    Gson gson = new Gson();

    String jsonStr =  gson.toJson(claims);

    response.setContentType("application/json; charset=UTF-8");

    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonStr);
    printWriter.close();
    
    }

}
