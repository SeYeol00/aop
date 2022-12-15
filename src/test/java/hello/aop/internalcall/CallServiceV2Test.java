package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV2Test {

    @Autowired // 빈 주입될 떄는 프록시로 주입됨
    CallServiceV2 callServiceV2;

    @Test
    void external(){
        log.info("target = {}", callServiceV2.getClass());
        callServiceV2.external();
        // 내부 매서드 호출(this.internal();)
        // internal이 aop 적용이 안되어 있다.
    }

    @Test
    void internal(){
        callServiceV2.internal();
    }
}