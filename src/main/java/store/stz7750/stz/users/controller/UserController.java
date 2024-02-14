package store.stz7750.stz.users.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import store.stz7750.stz.jwtutil.JwtUtil;
import store.stz7750.stz.users.service.UserService;
import store.stz7750.stz.users.vo.EmailVO;
import store.stz7750.stz.users.vo.UserVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.net.Inet4Address;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService service;

    @PutMapping("/join")
    public void addUser(@RequestBody UserVO vo , HttpServletRequest request)throws Exception{
        String agent = request.getHeader("USER-AGENT");

        String os = getClientOS(agent);
        String browser = getClientBrowser(agent);
        String ip = (String)request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown")) ip = (String)request.getRemoteAddr();
        vo.setOs(os);
        vo.setIp(ip);
        vo.setBrowser(browser);
        int result = service.addUser(vo);

        sendMail(vo.getEmail(), os, ip, browser);
    }

    public static Map<String, Object> broswserInfo(HttpServletRequest request){

        String agent = request.getHeader("USER-AGENT");

        String os = getClientOS(agent);
        String broswser = getClientBrowser(agent);
        String ip = (String)request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown")) ip = (String)request.getRemoteAddr();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("ip", ip);
        map.put("header", agent);
        map.put("os", os);
        map.put("broswser", broswser);
        return map;
    }



    public static String getClientOS(String userAgent) {
        String os = "";
        userAgent = userAgent.toLowerCase();
        if (userAgent.indexOf("windows nt 10.0") > -1) {
            os = "Windows10";
        }else if (userAgent.indexOf("windows nt 6.1") > -1) {
            os = "Windows7";
        }else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows nt 6.3") > -1 ) {
            os = "Windows8";
        }else if (userAgent.indexOf("windows nt 6.0") > -1) {
            os = "WindowsVista";
        }else if (userAgent.indexOf("windows nt 5.1") > -1) {
            os = "WindowsXP";
        }else if (userAgent.indexOf("windows nt 5.0") > -1) {
            os = "Windows2000";
        }else if (userAgent.indexOf("windows nt 4.0") > -1) {
            os = "WindowsNT";
        }else if (userAgent.indexOf("windows 98") > -1) {
            os = "Windows98";
        }else if (userAgent.indexOf("windows 95") > -1) {
            os = "Windows95";
        }else if (userAgent.indexOf("iphone") > -1) {
            os = "iPhone";
        }else if (userAgent.indexOf("ipad") > -1) {
            os = "iPad";
        }else if (userAgent.indexOf("android") > -1) {
            os = "android";
        }else if (userAgent.indexOf("mac") > -1) {
            os = "mac";
        }else if (userAgent.indexOf("linux") > -1) {
            os = "Linux";
        }else{
            os = "Other";
        }
        return os;
    }



    public static String getClientBrowser(String userAgent) {
        String browser = "";

        if (userAgent.indexOf("Trident/7.0") > -1) {
            browser = "ie11";
        }
        else if (userAgent.indexOf("MSIE 10") > -1) {
            browser = "ie10";
        }
        else if (userAgent.indexOf("MSIE 9") > -1) {
            browser = "ie9";
        }
        else if (userAgent.indexOf("MSIE 8") > -1) {
            browser = "ie8";
        }
        else if (userAgent.indexOf("Chrome/") > -1) {
            browser = "Chrome";
        }
        else if (userAgent.indexOf("Chrome/") == -1 && userAgent.indexOf("Safari/") >= -1) {
            browser = "Safari";
        }
        else if (userAgent.indexOf("Firefox/") >= -1) {
            browser = "Firefox";
        }
        else {
            browser ="Other";
        }
        return browser;
    }

    public void sendMail(String emailAddress, String os, String ip, String browser) throws Exception {

        EmailVO email = new EmailVO();

        String receiver = emailAddress; // Receiver.

        String subject = "보안 접속 메세지 입니다.";

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formatedNow = now.format(formatter);

        String content = "접속 일자 :"+formatedNow+"\n접속 운영체제 :"+os+"\n접속 ip :"+ip+"\n 접속 브라우저 :"+browser;

        email.setReceiver(receiver);
        email.setSubject(subject);
        email.setContent(content);

        Boolean result = service.sendMail(email);


    }

    @PostMapping("/login")
    public String login(@RequestBody UserVO vo) {
        vo.getId();
        vo.getPassword();
        return new String();
    }

    @GetMapping("/logSuccess")
    public String logSuccess(@RequestParam String param) {
        return new String();
    }
    

    public class AuthenticationResponse {
        private String token;

        public AuthenticationResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    /* @PostMapping("/logout")
    public String logout(@RequestBody String ) {
        //TODO: process POST request
        
        return entity;
    } */
    



}
