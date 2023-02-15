package coms.firstproject.domain.memberInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepositoryImpl(DataSource dataSource){
        /* DataSource: DB와 관계된 커넥션 정보를 담고있으며 빈으로 등록하여 인자로 넘겨준다.
        (Finally catch 등 순수 jdbc 사용시 기술했던 긴 로직을 줄여둔것  */
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Member save(Member member) {
        String QuerySave = "insert into member(name,id,pwd,call)values(?,?,?,?)";
        Object[] args = {member.getName(),member.getId(),member.getPwd(),member.getCall()};
        log.info("회원 가입 DB 실행");
        jdbcTemplate.update(QuerySave,args);
        return member;
    }

    @Override
    public List<Member> findById(String id){
        String QueryFindById = "select * from member where id = ?";
        log.info("특정 회원 검색 DB 실행");
        List<Member> result =  jdbcTemplate.query(QueryFindById, new MemberMapper(),id);
        return result;
    }

    @Override
    public List<Member> findAll() {
        String QueryFindAll = "select * from member";
        log.info("회원 목록 DB 실행");
        List<Member> result = jdbcTemplate.query(QueryFindAll, new MemberMapper());
        return result;
    }

    @Override
    public void update(Member member){
        String QueryUpdate = "update member set name=?,call=? where id=?";
        Object[] args = {member.getName(),member.getCall(),member.getId()};
        log.info("회원 수정 DB 실행");
        jdbcTemplate.update(QueryUpdate,args);
    }

    @Override
    public void delete(String name){
        String QueryDelete = "delete from member where name = ?";
        log.info("회원 삭제 DB 실행");
        jdbcTemplate.update(QueryDelete,name);
    } /** 변수에 있는 값을 가져와서 ? 에 넣어야 할 때는, update 옆에 받아온 변수를 넣어준다*/

    @Override
    public Member login(String id, String pwd) {
        String QueryLogin = "select * from member where id=? and pwd=?";
        try {
            return (Member)jdbcTemplate.queryForObject(QueryLogin, new MemberMapper(), id, pwd);
        }catch (EmptyResultDataAccessException e){
          return null;
        }
    }
}
