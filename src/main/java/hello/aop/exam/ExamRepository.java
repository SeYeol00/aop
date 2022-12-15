package hello.aop.exam;


import hello.aop.exam.annotation.Retry;
import hello.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {


    private static int seq = 0;


    /**
     * 다섯 번에 한 번 실패하는 요청
     * 보통 간헐적으로 어쩌다 한 번씩 에러가 튄다.
     * 이럴 떄 리트라이를 해야한다.
     * 다만 리트라이 횟수 제한을 걸어야한다.
     * 제한이 없으면 서비스 셀프 디도스가 발생한다.
     */
    @Trace
    @Retry(4) // 디폴트벨류를 여기서 지정 가능하다.
    public String save(String itemId) {
        seq++;
        if(seq % 5 == 0){
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
