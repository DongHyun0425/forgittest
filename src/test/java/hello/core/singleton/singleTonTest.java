package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class singleTonTest {
    @Test
    @DisplayName("스프링없는 순수 di 컨테이너")
    void PureContainer(){
        AppConfig appConfig = new AppConfig();
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();
        System.out.println("Memeberservice 1 = " + memberService1);
        System.out.println("Memeberservice 2 = " + memberService2);
        Assertions.assertThat(memberService1).isNotEqualTo(memberService2);
    }

    @Test
    @DisplayName("싱글톤 테스트")
    public void SingletonserviceTest(){
        SingleTonService singleTonService1 = SingleTonService.getInstance();
        SingleTonService singleTonService2 = SingleTonService.getInstance();
        System.out.println("singleTonSerivce1 ="+singleTonService1);
        System.out.println("singleTonSerivce2 ="+singleTonService2);
        Assertions.assertThat(singleTonService1).isSameAs(singleTonService2);
    }

    @Test
    @DisplayName("스프링 싱글톤")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);
        System.out.println("Memeberservice 1 = " + memberService1);
        System.out.println("Memeberservice 2 = " + memberService2);
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
