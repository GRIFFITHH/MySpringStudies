package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean // @Bean으로 인하여 스프링 컨테이너에 소속됨
    public MemberService memberService(){ // memberService = Key
        return new MemberServiceImpl(memberRepository()); // 객체 = Value
    }//MemberServiceImpl을 쓸지말지 제어권한은 AppConfig에게 있다. OrderServiceImpl도 마찬가지!

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){ // 오더서비스에 저장소와 할인정책을 주입시켜준다.
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    //따라서 MemberServiceImpl,OrderServiceImpl은 실행에만 집중해도된다.
    //주입은 AppConfig가 해줄테니니
}
