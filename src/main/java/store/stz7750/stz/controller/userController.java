package store.stz7750.stz.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import store.stz7750.stz.service.userService;
import store.stz7750.stz.vo.userVO;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.net.Inet4Address;
import java.util.List;

@RestController
@RequestMapping("/api")
public class userController {

    @Autowired
    userService service;

    @GetMapping("/userInfo")
    public List<userVO> selectUserInfo(HttpServletRequest request) throws Exception{
        List<userVO> userInfo = service.selectUserInfo();
        String ip = Inet4Address.getLocalHost().getHostAddress();
        System.out.println("ipv4"+ip);
        System.out.println("request"+broswserInfo(request));
        return userInfo;
    }

    @PutMapping("/join")
    public void addUser(@RequestBody userVO vo , HttpServletRequest request){
        String agent = request.getHeader("USER-AGENT");

        String os = getClientOS(agent);
        String browser = getClientBrowser(agent);
        String ip = (String)request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown")) ip = (String)request.getRemoteAddr();
        vo.setOs(os);
        vo.setIp(ip);
        vo.setBrowser(browser);
        int result = service.addUser(vo);
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

    public class EmailSender {

        public static void main(String[] args) throws Exception {
            String to = "받는사람@gmail.com"; // 받는 사람의 이메일 주소
            String from = "보내는사람@gmail.com"; // 보내는 사람의 이메일 주소
            String password = "보내는사람의 비밀번호"; // 보내는 사람의 이메일 계정 비밀번호
            String host = "smtp.gmail.com"; // 구글 메일 서버 호스트 이름

            // SMTP 프로토콜 설정
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");

            // 보내는 사람 계정 정보 설정
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            // 메일 내용 작성
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("메일 제목");
            msg.setText("메일 내용");

            // 메일 보내기
            Transport.send(msg);
        }
    }

}
