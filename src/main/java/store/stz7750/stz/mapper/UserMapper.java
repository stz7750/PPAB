package store.stz7750.stz.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.stz7750.stz.vo.UserVO;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVO> selectUserInfo();

    int insertUser(UserVO vo);
}
