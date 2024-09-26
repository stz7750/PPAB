package store.stz7750.stz.admin.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.stz7750.stz.admin.mapper.AdminMapper;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdminService {

    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    AdminMapper mapper;
    public List<EventVO> getAllNews(EventVO vo){
        return mapper.selectNews(vo);
    }

    public List<EventVO> getAllEvent(Integer eventNo){
        return  mapper.selectEvents(eventNo);
    }

    public List<Map<String, Object>> getLoginCountByType(Map<String, Object> params) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        return mapper.selectLoginCountByType(params);
    }

    public int upsertEvent(EventVO vo){
        int cntEvent = mapper.upsertEvent(vo);
        return cntEvent;
    }

    public List<Map<String, Object>> getMenu(){
        return mapper.selectMenu();
    }

    public List<Map<String, Object>> getAddr(Map<String, Object> params){
        return mapper.getAddr(params);
    }

    public List<Map<String, Object>> getAllAddrNm(Map<String,Object> params){
        if (params.get("endSigKorNm") == null ) {
            params.put("dynamicType", "addr");
        }
        return mapper.getAllAddrNm(params);
    }

    //TODO : map 타입이나,VO로 변경해서 데이터를 받고, 경로를 따로 지정해서 배치관련 파일을 따로 모을 예정.
    public void runBatch(String regBy) {
        mapper.insertCntntSummaryStats(regBy);
    }

    public void logBatchExecution(String jobName, LocalDateTime startTime, LocalDateTime endTime, long durationSeconds, String status, String errorMessage) {
        // LocalDateTime을 Timestamp로 변환
        Timestamp startTimestamp = Timestamp.valueOf(startTime);
        Timestamp endTimestamp = Timestamp.valueOf(endTime);

        // MyBatis 메서드 호출
        mapper.insertBatchHistory(jobName, startTimestamp, endTimestamp, durationSeconds, status, errorMessage);
    }
}
