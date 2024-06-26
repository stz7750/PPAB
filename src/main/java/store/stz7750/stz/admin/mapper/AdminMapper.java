package store.stz7750.stz.admin.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    List<EventVO> selectNews(EventVO vo);

    List<EventVO> selectEvents(Integer event);

    List<Map<String, Object>> selectLoginCountByType(Map<String, Object> params);

    int upsertEvent(EventVO vo);

    List<Map<String, Object>> selectMenu();
 }
