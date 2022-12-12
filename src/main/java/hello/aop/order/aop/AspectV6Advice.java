package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {


    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    // 포인트컷을 따로 빼줬다면 이렇게 경로를 입력해서 지정할 수 있다.
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            // @Before
            log.info("[트랜잭션 시작] {}",joinPoint.getSignature());
            // 실제 비즈니스 코드 joinPoint.proceed()를 안 하면 다음 매서드로 넘어가지 않는다.
            // 즉 프로씨드로 비즈니스 로직을 실행한다는 개념이다.
            // 여러번 호출도 가능하다.
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}",joinPoint.getSignature());
            return result;
        }catch (Exception e){
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}",joinPoint.getSignature());
            throw e;
        }finally {
            // @After
            log.info("[리소스 릴리즈] {}",joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        // ProceedingJoinPoint를 쓰지 못한다.
        // @Before는 joinPoint.proceed()가 필요 없다.
        log.info("[before] {}", joinPoint.getSignature());
    }

    // 뒤에 있는 변수 명과 파라미터 이름이 같아야한다.
    // return을 쓸 순 있지만 return값을 바꿀 순 없다.
    // aop가 걸리는 메서드의 반환타입을 result가 따른다.
    // 만약 메서드의 반환타입이 String이면 String 타입을 result로 받아야한다.
    // 그래서 모든 타입을 포괄하는 Object를 사용하는 것이 좋다.
    // 타입이 다르면 어드바이스가 호출 자체가 안된다.
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()",returning = "result")
    public void doReturn(JoinPoint joinPoint,Object result){
        log.info("[return] {} return = {}", joinPoint.getSignature(),result);
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()",returning = "result")
    public void doReturn2(JoinPoint joinPoint,String result){
        log.info("[return2] {} return = {}", joinPoint.getSignature(),result);
    }

    // 뒤에 있는 변수 명과 파라미터 이름이 같아야한다.
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()",throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[ex] {} message = {}",ex);

    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after] {}",joinPoint.getSignature());
    }

}
