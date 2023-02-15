package coms.firstproject.domain.memberInfo;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);


    List<Member> findById(String id);

    List<Member> findAll();

    void update(Member member);

    void delete(String name);

    Object login(String id, String pwd);
}
