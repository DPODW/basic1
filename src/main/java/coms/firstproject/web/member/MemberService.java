package coms.firstproject.web.member;

import coms.firstproject.domain.memberInfo.Member;
import coms.firstproject.domain.memberInfo.MemberRepository;
import javassist.bytecode.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class MemberService {

    MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member loginCheck(String id, String pwd){
        Object login = memberRepository.login(id, pwd);
        if(login == null){
            throw new NoSuchElementException("아이디 혹은 비밀번호 없음");
        }else{
            return (Member) login;
        }
    }

    public void insert(Member member){
        log.info("회원 가입 서비스 실행. . .");
        if(memberRepository.findById(member.getId()).isEmpty()){
            memberRepository.save(member);
        }else {
            throw new RuntimeException("아이디 중복 입니다");
        }
    }

    public List<Member> memberList(){
        log.info("회원 목록 서비스 실행. . .");
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public List<Member> getOne(String id){
        log.info("특정 회원 검색 서비스 실행. . .");
        List<Member> member = memberRepository.findById(id);
        return member;
    }

    public void nameDelete(String name){
        log.info("회원 삭제 서비스 실행. . .");
        memberRepository.delete(name);
    }

    public void memberEdit(Member member){
        log.info("회원 수정 서비스 실행. . .");
        memberRepository.update(member);
    }
}
