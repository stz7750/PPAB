package store.stz7750.stz.testCase.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Profile;
import store.stz7750.stz.admin.vo.EventVO;

import java.util.List;

@Mapper
public interface UserMapperTest {

    void insertRandomUserInfo(@Param("count") int count);

    List<EventVO> getEventList (String editor);

}
