package store.stz7750.stz.config.handler;

import java.io.PrintWriter;
import java.util.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
    throws IOException, ServletException {
        
        Gson gson = new Gson();
        
        String jsonStr = gson.toJson(Map.of("Error","ERROR_LOGIN"));

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr+"213123412413421321312321414213123");
        printWriter.close();
    }
}
