package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service: 컴포넌트 스캔과 자동 의존관계 설정
public class MemberService {

    private final MemberRepository memberRepository;
    // ↑ TestClass에서 객체를 생성하면 이것과 다른 객체를 생성하기 때문에 이를 막고자 코드를 수정
    // : 외부에서 객체를 받아옴
    // @Autowired: 컴포넌트 스캔과 자동 의존관계 설정
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 같은 이름이 있는 중복 회원x
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // = Optional<Member> 객체
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
                // ifPresent: Optional 클래스 함수. null이 아닐 시 괄호 안의 코드를 수행
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
