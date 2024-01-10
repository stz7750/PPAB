package store.stz7750.stz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.stz7750.stz.mapper.userMapper;
import store.stz7750.stz.vo.userVO;

import java.util.List;

@Service
public class userService {

    @Autowired
    userMapper mapper;
    public List<userVO> selectUserInfo(){
        return mapper.selectUserInfo();
    }

    public int addUser(userVO vo){
        return mapper.insertUser(vo);
    }
}
