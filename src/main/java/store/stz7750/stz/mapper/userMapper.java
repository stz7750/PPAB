package store.stz7750.stz.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.stz7750.stz.vo.userVO;

import java.util.List;

@Mapper
public interface userMapper {
    List<userVO> selectUserInfo();

    int insertUser(userVO vo);
}
