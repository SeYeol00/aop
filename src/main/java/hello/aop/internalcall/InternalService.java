package hello.aop.internalcall;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalService {

    public void internal(){
        log.info("call internal");
    }
}
