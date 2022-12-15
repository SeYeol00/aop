package hello.aop.exam.aop;


import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class RetryAspect {
    // 재시도를 하는 aop

    // 이 어노테이션이 달린 매서드에 적용할게요
    // 재시도를 할 때 언제 다시 프로씨드를 호출할지 결정해야하기 때문에 어라운드를 쓴다.
    @Around("@annotation(retry)") // 파라미터로 받으면 저렇게 길게 쓸 필요 없이 이름만 맞춰도 된다.
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} args = {}", joinPoint.getSignature(),retry);

        // 어노테이션이 담고 있는 값
        int maxRetry = retry.value();
        // 익셉션을 담을 그릇
        Exception exceptionHolder = null;

        // 재시도 핵심 코드, 어스펙트 로직
        for(int retryCount = 1; retryCount<=maxRetry;retryCount++){
            try {
                log.info("[retry] try count = {}/{}",retryCount,maxRetry);
                return joinPoint.proceed();
            } catch (Exception e){
                // 익셉션을 담아놔야한다.
                exceptionHolder = e;
            }
        }
        throw  exceptionHolder;
    }
}
