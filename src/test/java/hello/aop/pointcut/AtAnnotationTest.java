package hello.aop.pointcut;


import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(AtAnnotationTest.AtAnnotationAspect.class)
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("hello");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect{
        // 이 어노테이션이 적용된 곳에 Aop를 적용한ㄷ.
        @Around("@annotation(hello.aop.member.annotation.MethodAop)")
        public Object doAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
