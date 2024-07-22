package store.stz7750.stz.admin.InjecttionAspect;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import store.stz7750.stz.config.filter.SqlInjectionFilter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * packageName    : stz-store.stz7750.stz.admin.InjecttionAspect
 * fileName       : SqlInjectionAspect
 * author         : stz
 * date           : 7/22/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/22/24        stz       최초 생성
 */
@Aspect
@Component
@Log4j
public class SqlInjectionAspect {

    @Before("execution(* store.stz7750.stz.admin..*(..))")
    public void SqlInjectionFilter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof String param) {
                if (SqlInjectionFilter.containsSQLKeywords(param)) {
                    log.error("!!!!!!!!!!!!!!!!!!!!!!!!! : injection !");
                    throw new IllegalArgumentException("인젝션 발생.");
                }
            }else{
                //TODO : 문자열 타입의 파라미터만 일단 가져오게끔 만들었는데 파라미터의 오브젝트 타입으로 가져와서 배열 필드로 변환
                // 변환 한 배열을 순회하면서 DML, DDL 같은 문자가 포함되어있는지 체크 후 throw
                log.error("문자열 타입의 파라미터가 아님.");
            }
        }
    }
}


