package store.stz7750.stz.testCase.user.controller;

/**
 * packageName    : stz-store.stz7750.stz.testCase.user.controller
 * fileName       : ProcedurceTest
 * author         : stz
 * date           : 10/2/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/2/24        stz       최초 생성
 */

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import store.stz7750.stz.testCase.user.service.UserServiceTest;

@SpringBootTest
@Log4j
@Transactional
public class ProcedurceTest {

    @Autowired
    private UserServiceTest userService;

    @Test
    @DisplayName("toTest: DB에 직접 접근하고 롤백되는 테스트")
    public void toTest() {
        log.info("============== 테스트 시작 =============");

        // 실행할 서비스 메서드 호출
        int count = 1000;
        userService.insertRandomUsers(count);

        log.info("============== 테스트 종료 =============");
    }
}