package store.stz7750.stz.admin.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    List<NewsVO> selectNews(Integer cno);

    List<EventVO> selectEvents(Integer event);

    List<Map<String, Object>> selectLoginCountByType(Map<String, Object> params);

    int upsertEvent(NewsVO vo);
}
