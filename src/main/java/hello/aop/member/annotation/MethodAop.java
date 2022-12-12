package hello.aop.member.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)// 매서드에 붙일 수 있는 에노테이션
@Retention(RetentionPolicy.RUNTIME)// 런타임 동안 살아있는 어노테이션
public @interface MethodAop {
    // 어노테이션도 값을 가질 수 있다.
    String value();
}
