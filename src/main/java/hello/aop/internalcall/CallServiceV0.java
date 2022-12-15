package hello.aop.internalcall;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class CallServiceV0 {

    public void external(){
        log.info("call external");
        // 중요, 내부 매서드 호출(this.internal();)
        internal();//앞에 this.가 생략된
    }

    public void internal(){
        log.info("call internal");
    }
}
