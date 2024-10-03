
package store.stz7750.stz.testCase.user.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.log4j.Log4j;
import store.stz7750.stz.testCase.user.service.UserServiceTest;

@WebMvcTest(TestUserController.class)
@AutoConfigureMockMvc(addFilters = false) // srping security를 위한 스킵
@Log4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceTest userService;

    @Test
    @DisplayName("랜덤 유저 데이터를 삽입하는 API가 200 OK를 반환해야 한다")
    public void testInsertRandomUsers() throws Exception {
        int count = 1000;

        // /api/users/insert-random 엔드포인트로 POST 요청을 보냄
        mockMvc.perform(post("/api/users/insert-random")
                        .param("count", String.valueOf(count)))
                .andExpect(status().isOk()) // 상태 코드가 200 OK 인지 확인
                .andExpect(content().string("통과")); // 응답 메시지가 올바른지 확인

        // UserService의 insertRandomUsers(count)가 호출되었는지 확인
        verify(userService).insertRandomUsers(count);

        log.info("===========================테스트 종료======================");

    }

    @Test
    @DisplayName("NullPointerException 발생 API 테스트")
    public void testInsertRandomUsersWithNullPointerException() throws Exception {
        mockMvc.perform(post("/api/users/insert-random")
                        .param("count", "null")) // 잘못된 파라미터 전달
                .andExpect(status().isInternalServerError()); // 500 에러 기대

        log.info("===========================테스트 종료======================");
    }
}