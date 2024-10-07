package store.stz7750.stz.testCase.user.service;

import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.testCase.user.mapper.UserMapperTest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceTest {

    @Autowired
    UserMapperTest userMapper;

    public void insertRandomUsers(int count) {
        userMapper.insertRandomUserInfo(count);
    }

    public List<EventVO> getEventList(String editor) {
        return userMapper.getEventList(editor);
    }
}