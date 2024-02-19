package store.stz7750.stz.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.stz7750.stz.admin.service.AdminService;
import store.stz7750.stz.admin.vo.EventVO;
import store.stz7750.stz.admin.vo.NewsVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class AdminController {

    @Autowired
    AdminService service;
    /* 메인페이지 뉴스 */
    @GetMapping(value={"/news","/news/{cno}"})
    public List<NewsVO> getAllNews(@PathVariable(required = false) Integer cno) {
        List<NewsVO> vo =  service.getAllNews(cno);
        return vo;
    }

    /*메인페이지 이벤트 */
    @GetMapping(value={"/event", "/event/{eventNo}"})
    public List<EventVO> getAllEvent(@PathVariable(required = false) Integer eventNo){
        return service.getAllEvent(eventNo);
    }

    /*관리자 메인페이지 type(일,월,년)별 방문자 수 */
    @GetMapping(value={"/loginCounts/{type}"})
    public List<Map<String, Object>> getLoginCountsByType(@PathVariable String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", (type != null) ? type : "daily");
        return service.getLoginCountByType(params);
    }
}
