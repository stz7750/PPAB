package store.stz7750.stz.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import store.stz7750.stz.admin.service.AdminService;

/**
 * packageName    : stz-store.stz7750.stz.config.batch
 * fileName       : BatchConfig
 * author         : stz
 * date           : 9/22/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/22/24        stz       최초 생성
 */
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final AdminService adminService;
    @Scheduled(cron = "0 35 18 * * ?")
    public void runBatchJob() {
        adminService.runBatch(null);
    }
}

