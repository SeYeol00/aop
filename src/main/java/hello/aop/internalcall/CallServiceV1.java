package hello.aop.internalcall;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {
    // 자기 자신을 주입받아서 해결하는 방식
    // 다만 여기서는 생성자 주입을 쓰면 안된다.
    // 생성자 주입을 사용하면 순환 사이클 에러가 생긴다.
    // 매서드 주입 방식(Setter 방식)을 사용할 것이다.
    // 생성자 주입, 필드 주입, 매서드 주입은 자주 나오므로 유념하기
    // 스프링부트 2.6부터는 스프링이 순환참조를 기본적으로 금지하도록 해놨다.
    // application.properties에 spring.main.allow-circular-references=true 설정을 넣어주자
    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter = {}",callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external(){
        log.info("call external");
        callServiceV1.internal();// 외부 매서드 호출
    }

    public void internal(){
        log.info("call internal");
    }


}
