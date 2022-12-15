package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV0Test {

    @Autowired // 빈 주입될 떄는 프록시로 주입됨
    CallServiceV0 callServiceV0;

    @Test
    void external(){
        log.info("target = {}", callServiceV0.getClass());
        callServiceV0.external();
        // 내부 매서드 호출(this.internal();)
        // internal이 aop 적용이 안되어 있다.
    }

    @Test
    void internal(){
        callServiceV0.internal();
    }
}