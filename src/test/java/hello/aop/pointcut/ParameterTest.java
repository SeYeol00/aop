package hello.aop.pointcut;


import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {
    // aop로 매개변수 전달


    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect{

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember(){};

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            // getArgs로 꺼낼 수 있다.        인덱싱으로 지정한다.
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg = {}", joinPoint.getSignature(),arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)") // 이름을 매칭하면 파라미터를 받을 수 있다.
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs1]{}, arg = {}", joinPoint.getSignature(),arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)") // 이름을 매칭하면 파라미터를 받을 수 있다.
        public void logArgs3(String arg) {
            log.info("[logArgs1], arg = {}",arg);
        }

        @Before("allMember() && this(obj)") // 객체 인스턴스에 오브잭트가 넘어온다.
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            // this는 프록시 객체를 호출하고
            log.info("[this]{}, obj = {}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)") // 객체 인스턴스에 오브잭트가 넘어온다.
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            // target은 프록시 대상이되는 실제 타겟을 호출한다.
            log.info("[target]{}, obj = {}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            // 어노테션 정보를 그대로 가져올 수 있다.
            log.info("[@target]{}, obj = {}",joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            // 어노테션 정보를 그대로 가져올 수 있다.
            log.info("[@within]{}, obj = {}",joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            // 어노테션 정보를 그대로 가져올 수 있다.
            log.info("[@within]{}, annotationValue = {}",joinPoint.getSignature(), annotation.value());
        }
    }
}
