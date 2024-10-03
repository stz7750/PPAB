package store.stz7750.stz.users.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import store.stz7750.stz.users.mapper.UserMapper;
import store.stz7750.stz.users.vo.EmailVO;
import store.stz7750.stz.users.vo.UserVO;

import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The type User service.
 */
@Service
@Log4j2
@Transactional
public class UserService {

    @Autowired
    protected JavaMailSender mailSender;
    @Autowired
    UserMapper mapper;
    private JdbcTemplate jdbcTemplate;

    /**
     * Findby user info user vo.
     *
     * @param id the id
     * @return the user vo
     */
    public UserVO findbyUserInfo(String id){
        return mapper.findByUserInfo(id);
    }

    /**
     * Select user info user vo.
     *
     * @param id       the id
     * @param password the password
     * @return the user vo
     */
    public UserVO selectUserInfo(String id, String password){
        //jdbcTemplate.update("INSERT INTO userInfo(user_name, tel, exchangeTime, ac, exchangeType) values (?,?,?,?,?)", id, password);
        return mapper.selectUserInfo(id,password);
    }

    /**
     *
     * @작성자 : stz
     * @작성일 : 2024 02 23
     * @버전 : 1.0.0
     * @description : 가입 유저의 요청 정보를 가져와 유효성 검사를 실시 합니다.
    ====================================================

     * @param vo
     * @return
     */
    public int addUser(UserVO vo){
        vo.setRole("member");
        String userId = vo.getId();
        String password = vo.getPassword();
        String email = vo.getEmail();
        String name = vo.getName();
        UserVO user = findbyUserInfo(userId);
        boolean passwordLength = password.length() >= 8 && password.length() <= 15;
        boolean username = name.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        if(user == null && passwordLength && !username){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            vo.setPassword(passwordEncoder.encode(password));
            return mapper.insertUser(vo);
        } else {
            return 0;
        }
    }


    public boolean sendMail(EmailVO email) throws Exception {

        try {

            MimeMessage msg = mailSender.createMimeMessage();

            msg.setSubject(email.getSubject());

            // 일반 텍스트만 전송하려는 경우
            /*msg.setText(email.getContent());*/

            // HTML 컨텐츠를 전송하려면.
            msg.setContent(email.getContent(), "text/html;charset=utf-8");
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getReceiver()));//수신자 setting

            mailSender.send(msg);

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return false;

    }

    public void logUserLogin(String userId, String username, String etc) {
        LocalDateTime currentLoginTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentLoginTime);
        mapper.insertLoginLog(userId, username, timestamp, etc);
    }

    public void insertRandomUsers(int count) {
        mapper.insertRandomUserInfo(count);
    }

    public void addMenuAccessLog(Map<String ,Object> MenuAccessLog){
        mapper.insertMenuAccessLog(MenuAccessLog);
    }

}
