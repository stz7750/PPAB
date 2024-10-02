package store.stz7750.stz.testCase.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Profile;

@Mapper
public interface UserMapperTest {

    void insertRandomUserInfo(@Param("count") int count);

}
