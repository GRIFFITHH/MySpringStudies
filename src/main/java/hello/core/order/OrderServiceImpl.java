package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    //회원정보를 가져오기위해 객체 선언
    private final MemberRepository memberRepository;// = new MemoryMemberRepository();

    //1) 할인정책을 쓰기위해서 객체생성
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //2) 정책변경
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //3) 정책을 바꾸는 순간 Fix를 Rate로 바꿔줘야한다. 이는 DIP와 OCP에 위반이며 사실은 구현체에도 의존하고 있었던 것


    private  DiscountPolicy discountPolicy; // 인터페이스에만 의존하는 코드 good
    //하지만 이상태로 돌리면 null point exeption ,discountPolicy 에 할당된값이 없기때문! (DIP만 지키는 상태)



    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override//주문을 한 고객이 어떤 멤버쉽이냐에 따라서 할인받는 정보를 반환하기 위한 메서드 (상속받음)
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = new MemoryMemberRepository().findById(memberId);
        int disdountPrice = discountPolicy.discount(member,itemPrice);

        return new Order(memberId,itemName,itemPrice,disdountPrice);
    }
}
