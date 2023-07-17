package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy(); // `AppConfig`에서 할인 정책 역할을 담당하는 구현을 `FixDiscountPolicy` -> `RatediscountPolicy`객체로 변경했다.
        return new RateDiscountPolicy();  // 이제 할인 정책을 변경해도, 애플리케이션의 구성역할을 담당하는 AppConfig만 변경하면 된다. 클라이언트 코드인 `OrderServiceImpl`를 포함해서 사용영역의 어떤 코드도 변경할 필요가 없다.
    }
}

// AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결) 해준다.
// `MemberServiceImpl` -> `MemoryMemberRepository`
// `OrderServiceImpl` -> `MemoryMemberRepository`, `FixDiscountPolicy`
