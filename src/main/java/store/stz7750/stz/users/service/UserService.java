package store.stz7750.stz.users.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import store.stz7750.stz.users.mapper.UserMapper;
import store.stz7750.stz.users.vo.EmailVO;
import store.stz7750.stz.users.vo.UserVO;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Service
@Log4j2
@Transactional
public class UserService {

    @Autowired
    UserMapper mapper;

    private JdbcTemplate jdbcTemplate;

    public UserVO findbyUserInfo(String id){
        return mapper.findByUserInfo(id);
    }
    public UserVO selectUserInfo(String id, String password){
        //jdbcTemplate.update("INSERT INTO userInfo(user_name, tel, exchangeTime, ac, exchangeType) values (?,?,?,?,?)", id, password);
        return mapper.selectUserInfo(id,password);
    }

    public int addUser(UserVO vo){
        vo.setRole("member");
        return mapper.insertUser(vo);
    }

    @Autowired
    protected JavaMailSender mailSender;

    public boolean sendMail(EmailVO email) throws Exception {

        try {

            MimeMessage msg = mailSender.createMimeMessage();

            msg.setSubject(email.getSubject());

            // 일반 텍스트만 전송하려는 경우
            msg.setText(email.getContent());

            // HTML 컨텐츠를 전송하려면.
            //msg.setContent("<a href='https://www.naver.com/'>클릭</a>", "text/html;charset=utf-8");
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getReceiver()));//수신자 setting

            mailSender.send(msg);

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return false;

    }


}
