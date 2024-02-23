package store.stz7750.stz.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.stz7750.stz.admin.mapper.AdminMapper;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    AdminMapper mapper;
    public List<NewsVO> getAllNews(Integer cno){
        return mapper.selectNews(cno);
    }

    public List<EventVO> getAllEvent(Integer eventNo){
        return  mapper.selectEvents(eventNo);
    }

    public List<Map<String, Object>> getLoginCountByType(Map<String, Object> params) {
        return mapper.selectLoginCountByType(params);
    }

    public int upsertEvent(NewsVO vo){
        int cntEvent = mapper.upsertEvent(vo);
        return cntEvent;
    }
}
