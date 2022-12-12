package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    // 포인트컷들만 따로 뺴서 모듈화할 수 있다.


    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} // pointcut signature


    // 클래스 이름 패턴이 *Service인 것
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){} // pointcut signature


    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
