package store.stz7750.stz.users.mapper;


import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import store.stz7750.stz.users.vo.UserVO;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM userInfo WHERE id = #{id}")
    UserVO findByUserInfo(@Param("id") String id);

    @Select("SELECT * FROM userinfo WHERE id = #{id} AND password = #{password}")
    UserVO selectUserInfo(@Param("id")String id, @Param("password")String password);

    int insertUser(UserVO vo);

    
}
