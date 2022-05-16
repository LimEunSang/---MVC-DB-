package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // ↑ 이미 MemoryMemberRepository에서 생성한 객채와 다른 객체를 생성: 내용물이 달라질 수 있음
    MemoryMemberRepository memberRepository;

    // 동일한 MemberRepository 객체 사용
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); // Dependency Injection (DI)
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given: 무언가 주어졌는데
        Member member = new Member();
        member.setName("hello");

        //when: 이거를 실행했을 때
        Long saveId = memberService.join(member);

        //then: 결과가 이게 나와야 되
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

/*
        try {
            memberService.join(member1);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // ↑ [tip] ctrl + alt + v: Extract Variable
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}