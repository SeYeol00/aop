package hello.aop.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어노테이션이 어디까지 적용 돼?
@Retention(RetentionPolicy.RUNTIME)// 어노테이션이 언제까지 살아있어?
public @interface Trace {
    // @Trace 어노테이션을 달고 있으면 프록시가 적용이 되게 만들 것이다.
    // 실무에서 주로 사용하는 방식으로 잘 알아두자
    // execution과 @annotation을 &&로 묶어서 주로 사용한다.
    // 실무 팁이다.
}
