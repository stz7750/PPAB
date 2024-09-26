package store.stz7750.stz.admin.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    List<EventVO> selectNews(EventVO vo);

    List<EventVO> selectEvents(Integer event);

    List<Map<String, Object>> selectLoginCountByType(Map<String, Object> params);

    int upsertEvent(EventVO vo);

    List<Map<String, Object>> selectMenu();

    List<Map<String, Object>> getAddr(Map<String, Object> params);

    List<Map<String, Object>> getAllAddrNm(Map<String, Object> params);

    void insertCntntSummaryStats(@Param("regBy") String regBy);

    void insertBatchHistory(
            @Param("jobName") String jobName,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime,
            @Param("durationSeconds") long durationSeconds,
            @Param("status") String status,
            @Param("errorMessage") String errorMessage
    );
 }
