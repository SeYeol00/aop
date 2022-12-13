package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {


    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);

    }

    @Test
    void printMethod(){
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod = {}",helloMethod);
    }
    @Test
    void exactMatch(){ // 가장 세부적인 포인트컷 표현식
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch(){// 가장 많이 생략한 포인트컷 표현식
        pointcut.setExpression("execution(* *(..))");// 파라미터 타입과 파라미터 수가 상관 없다.
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..))"); // 이름이 hello인 매서드
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1(){
        pointcut.setExpression("execution(* hell*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2(){
        pointcut.setExpression("execution(* *ll*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse(){
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatchFalse(){ // 패키지명이 중요하다. 서브 패키지가 틀린 케이스
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    @Test                           // 점 하나는 정확한 위치, 점 두 개는 그 패키지와 하위 패키지 포함
    void packageMatchSubPackage1(){ // 서브 패키지 자리에 점이 두 개다.
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test                           // 점 하나는 정확한 위치, 점 두 개는 그 패키지와 하위 패키지 포함
    void packageMatchSubPackage2(){ // 서브 패키지 자리에 점이 두 개다.
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeExactMatch(){
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType(){ // 부모타입으로 포인트컷을 지정해도 매칭된다.
        // 물론 부모는 자식을 품을 수 있고 자식은 부모를 품을 수 없는 다형성을 생각해보면 당연한 것
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        // 자식 타입에 있는 다른 매서드까지 매칭이 되는지 확인하기
        // 즉 오버로딩 안 한 매서드가 되는가
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method interalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(interalMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        // 자식 타입에 있는 다른 매서드까지 매칭이 되는지 확인하기
        // 즉 오버로딩 안 한 매서드가 되는가
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method interalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(interalMethod,MemberServiceImpl.class)).isFalse();
    }


    // String 타입의 파라미터 허용
    // (String)
    @Test
    void argsMatch() throws NoSuchMethodException {
        //                                    타입이 들어가는 칸
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    // 파라미터가 없어야 함
    // ()
    @Test
    void argsMatchNoArgs() throws NoSuchMethodException {
        //                                    타입이 들어가는 칸
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    // (Xxx)
    @Test
    void argsMatchStar() throws NoSuchMethodException {
        //                                    타입이 들어가는 칸
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    // 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    // (Xxx)
    @Test
    void argsMatchAll() {
        //                                    타입이 들어가는 칸
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    // String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    // (String), (String, Xxx), (String, Xxx, Xxx)
    @Test
    void argsMatchComplex() {
        //                                    타입이 들어가는 칸
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


}
