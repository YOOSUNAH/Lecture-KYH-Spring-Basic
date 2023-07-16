package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
     // 인터페이스에만 의존하기
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존하도록 설계와 코드를 변경했다. 실제 실행을 해보면 null pointer exception NPE가 발생한다.
    // 이 문제를 해결하려면 누군가가 클라이언트인 `OrderServiceImpl` 에 `DiscountPolicy`의 구현 객체를 대신 생성하고 주입해주어야 한다.

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);  // 회원정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice);  //할인정책에다가 회원(member)을 넘겨

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
