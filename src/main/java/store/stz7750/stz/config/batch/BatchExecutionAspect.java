package store.stz7750.stz.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import store.stz7750.stz.admin.service.AdminService;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * packageName    : stz-store.stz7750.stz.config.batch
 * fileName       : BatchExecutionAspect
 * author         : stz
 * date           : 9/22/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/22/24        stz       최초 생성
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class BatchExecutionAspect {

    private final AdminService adminService;

    // AdminService의 runBatch 메서드와 다른 특정 서비스의 메서드를 포인트컷으로 정의
    @Pointcut("execution(* store.stz7750.stz.admin.service.AdminService.runBatch(..))")
    public void adminServiceBatch() {}


    @Around("adminServiceBatch()")
    public Object logBatchExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // 배치 시작 시간 기록
        LocalDateTime startTime = LocalDateTime.now();
        String jobName = joinPoint.getSignature().getName();  // 실행된 메서드 이름으로 배치 이름 설정

        log.info("Batch '{}' started at: {}", jobName, startTime);

        Object result = null;
        String status = "SUCCESS";
        String errorMessage = null;

        try {
            //기존 배치 작업의 영향이 없기 하기 위해서 그대로 진행을 하게 된다고 한다.
            result = joinPoint.proceed();
        } catch (Exception e) {
            status = "FAILED";
            errorMessage = e.getMessage();
            log.error("Batch '{}' failed with error: {}", jobName, errorMessage);
            throw e;
        } finally {
            // 배치 종료 시간 기록
            LocalDateTime endTime = LocalDateTime.now();
            long durationSeconds = Duration.between(startTime, endTime).toMillis();

            log.info("Batch '{}' completed at: {}", jobName, endTime);
            log.info("Batch '{}' duration: {} seconds", jobName, durationSeconds);

            // 배치 실행 정보 DB에 기록
            adminService.logBatchExecution(
                    jobName,
                    startTime,
                    endTime,
                    durationSeconds,
                    status,
                    errorMessage
            );
        }

        return result;
    }


}

