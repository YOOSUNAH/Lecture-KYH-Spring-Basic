package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscuntPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private final DiscountPolicy discountPolicy = new FixDiscuntPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); //인터페이스와 추상클래스 모두 의존해서 DIP 위반
    private DiscountPolicy discountPolicy; // 인터페이스에만 의존하게 변경 -> nullPointException 터짐
    // 이 문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
ㄹㄹ