package store.stz7750.stz.testCase.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import store.stz7750.stz.testCase.batch.service.BatchService;

/**
 * packageName    : stz-store.stz7750.stz.testCase.batch.config
 * fileName       : runBatchTest
 * author         : stz
 * date           : 10/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/4/24        stz       최초 생성
 */
@EnableScheduling
@Configuration
@RequiredArgsConstructor
@Log4j2
public class RunBatchTest {
    @Autowired
    BatchService batchService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Test
    @DisplayName("배치를 테스트하게 될 테스트")
    public void doBatchTest () {
        log.info("----------------테스트 종료-------------------");
    }
}

