package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
@Slf4j
@Aspect
public class AspectV1 {

    //                  포인트 컷
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        // doLog -> advice
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        // proceed를 해야 호출이 된다.
        return joinPoint.proceed();
    }
}
