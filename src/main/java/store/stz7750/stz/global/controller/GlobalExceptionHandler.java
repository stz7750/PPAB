package store.stz7750.stz.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import store.stz7750.stz.admin.service.AdminService;
import store.stz7750.stz.jwtutil.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : stz-store.stz7750.stz.global.controller
 * fileName       : GlobalExceptionHandler
 * author         : stz
 * date           : 10/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/3/24        stz       최초 생성
 */
@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final AdminService adminService;

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception e, WebRequest request) {
        String errorMessage = e.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.info("Exception occurred: {}, HTTP Status: {}", e.getClass().getName(), status);
        log.info("Exception message: {}", errorMessage);

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            Map<String, Object> claims = JwtUtil.validateToken(token);
            Map<String, Object> logData = new HashMap<>();
            String userId = (String) claims.get("id");
            logData.put("requestId", userId);
            logData.put("httpStatus", status.value());
            logData.put("errorMessage", errorMessage);
            logData.put("requestDate", new Date());
            logData.put("requestLocation", request.getDescription(false));

           adminService.addWebLog(logData);
        }
    }

}

