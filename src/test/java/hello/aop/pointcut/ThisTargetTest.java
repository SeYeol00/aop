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

@Slf4j // properties도 설정이 가능합니다.
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 프록시
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;


    @Test
    void success(){
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");

    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect{
        // 부모 타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[This-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();

        }

        // 부모 타입 허용
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();

        }

        // 부모 타입 미허용
        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[This-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();

        }

        // 부모 타입 미허용
        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[Target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();

        }


    }
}
