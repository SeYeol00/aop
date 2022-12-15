package hello.aop.proxyvs;


import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})// JDK 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})// CGLIB 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;


    @Test
    void go(){
        log.info("memberService class = {}", memberService.getClass());

        // 프록시 객체는 당장 타겟을 모르는데 구체 클래스의 타입을 주입할 수 없다.
        // 실제 프록시가 넘어와서 안된 것이다.
        // 인터페이스를 기반으로 프록시가 생겨서 JDK 동적 프록시 기반은 구체를 모른다.
        // CGLIB는 구체 클래스를 기반으로 프록시가 만들어져서 확인이 된다.
        // 구체 클래스는 인터페이스를 상속받았기 때문에 당연히 인터페이스도 안다.
        // 즉, CGLIB 프록시는 대상 객체인 구체 클래스 타입에 의존관계를 주입할 수 있다.
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberService.hello("hello");
    }
}
