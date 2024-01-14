package store.stz7750.stz.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.stz7750.stz.mapper.UserMapper;
import store.stz7750.stz.vo.EmailVO;
import store.stz7750.stz.vo.UserVO;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;
    public List<UserVO> selectUserInfo(){
        return mapper.selectUserInfo();
    }

    public int addUser(UserVO vo){
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
