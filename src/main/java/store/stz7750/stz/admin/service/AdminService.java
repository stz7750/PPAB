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
}
