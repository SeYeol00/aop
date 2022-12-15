package hello.aop.internalcall;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;


/**
 * 구조를 변경 혹은 분리하는 것
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {
    // 스프링에서 가장 권장하는 방법 - 구조 변경
    // 그냥 새 객체를 생성하는 것이다. - InternalService
    // public 매서드 - public 매서드 간의 내부 호출은
    // 외부 호출로 바꾸는 것이 좋다.
    private final InternalService internalService;

    public void external(){
        log.info("call external");
        internalService.internal();// 외부 매서드 호출
    }




}
