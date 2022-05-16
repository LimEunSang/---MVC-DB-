package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // private final MemberService memberService = new MemberService();
    // ↑ MemberService를 이용하는 각 Controller마다 객체를 생성할 필요가 없음
    // 따라서 Spring Container에 등록(한 객체: Spring Bean)하고 사용함: 하나의 객체만 사용 가능(싱글톤)

    // @Autowired private MemberService memberService; // 필드 주입: Bad!

    /* setter 주입: public으로 노출해야만 한다.
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    */

    // 생성자 주입: 가장 Best!
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // ↑ [tip] 생성자 자동 생성: Alt + Insert

    // Get: 조회할 때 주로 사용
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    // Post: 전달할 때 주로 사용
    @PostMapping("members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
