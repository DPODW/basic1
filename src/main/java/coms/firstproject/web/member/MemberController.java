package coms.firstproject.web.member;

import coms.firstproject.domain.memberInfo.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
public class MemberController {
    MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "html/Signin";
    }


    @PostMapping("/login") /** 값을 받고, 쿠키를 던지는 행위 */
    public String login(@RequestParam("id") String id,
                        @RequestParam("pwd") String pwd,
                        HttpServletResponse response
                        )
    {   log.info("{}",id);
        log.info("{}",pwd);
        memberService.loginCheck(id, pwd);
        Cookie cookie = new Cookie("id",id);
        response.addCookie(cookie);
            return "html/index_login";
        }


    @GetMapping("index_login")
    public String index_login(){
        return "html/index_login";
    }


    @ResponseBody
    @RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(int i = 0; i<cookies.length; i++){
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
        return "로그아웃 완료";
    }


    @GetMapping("/join")
    public String joinForm(){
        log.info("회원 가입 폼 컨트롤러 실행. . .");
        return "html/Signup";
    }


    @PostMapping("/join")
    public String join(@RequestParam("username") String name,
                       @RequestParam("Jid") String id,
                       @RequestParam("Jpwd") String pwd,
                       @RequestParam("Jcall") String call, Model model
                       ){
        Member member = new Member();
        member.setName(name);
        member.setId(id);
        member.setPwd(pwd);
        member.setCall(call);
        log.info("회원 가입 컨트롤러 실행. . .");
        memberService.insert(member);
        model.addAttribute("member",member);
        return "html/Member";
    }


    @GetMapping("/members")
    public String members(Model model){
       log.info("회원 목록 컨트롤러 실행. . .");
       List<Member> members = memberService.memberList();
       model.addAttribute("members",members);
       return "html/Members";
    }


    @GetMapping("/member") /** 로그인 상태 에서만 동작하게 하는게 좋을듯 */
    public String member(){
        return "html/Member";
    }


    @RequestMapping(value = "/{name}/delete", method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@PathVariable("name") String name){
        log.info("회원 삭제 컨트롤러 실행. . .");
        memberService.nameDelete(name);
        return "redirect:/members"; /* members 컨트롤러로 리다이렉트 매핑하여서 최신화 된 목록을 받아옴*/
    }


    @GetMapping("/{id}/editForm")
    public String editForm(@PathVariable("id")String id, Model model){
        log.info("회원 수정 폼 컨트롤러 실행. . .");
        List<Member> member = memberService.getOne(id);
        model.addAttribute("member",member);
        return "html/SignEdit";
    }


    @PostMapping("/{id}/editForm")
    public String edit(@PathVariable("id")String id,
                       @RequestParam("username") String name,
                       @RequestParam("call") String call,
                       @RequestParam("pwd") String pwd,
                       Model model){
        Member member = new Member();
        member.setId(id);
        member.setName(name);
        member.setCall(call);
        member.setPwd(pwd);
        log.info("회원 수정 컨트롤러 실행 . . .");
        memberService.memberEdit(member);
        model.addAttribute("member" , member);
        return "html/Member";
    }

}
