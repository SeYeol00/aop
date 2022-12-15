package hello.aop.internalcall;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {
    // 지연 조회 방식
    // 늦게 조회를 한다.
     //private final ApplicationContext applicationContext;

    private final ObjectProvider<CallServiceV2> callServiceProvider;


    public void external(){
        log.info("call external");
        // 무식한 방법이지만 이게 먹힐 때가 많다.
        // 즉, 지연해서 꺼내는 것이다.

        //CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();// 외부 매서드 호출
    }

    public void internal(){
        log.info("call internal");
    }


}
