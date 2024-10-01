package store.stz7750.stz.testCase.user.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import store.stz7750.stz.testCase.user.mapper.UserMapperTest;

@Service
@RequiredArgsConstructor
@Profile("test")
public class UserServiceTest {

    private final UserMapperTest userMapper;

    public void insertRandomUsers(int count) {
        userMapper.insertRandomUserInfo(count);
    }
}