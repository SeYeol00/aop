package hello.aop.exam.aop;


import hello.aop.exam.annotation.Trace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect // 꼭 스프링 빈으로 등록하자
public class TraceAspect {

    // Before는 조인포인트 고민 안 해도 되서 좋음
    // 실무에서 이렇게 어노테이션으로 포인트컷을 많이 쓴다.
    // 만들어둔 어노테이션을 적용 대상에 붙이기만 하면 되니까
    @Before("@annotation(hello.aop.exam.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} target = {}", joinPoint.getSignature(),args);
    }
}
