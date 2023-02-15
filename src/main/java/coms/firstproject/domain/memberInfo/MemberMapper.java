package coms.firstproject.domain.memberInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@Slf4j
public class MemberMapper implements RowMapper {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setName(rs.getString("name"));
        member.setId(rs.getString("id"));
        member.setPwd(rs.getString("pwd"));
        member.setCall(rs.getString("call"));
        return member;
    }
    /**
     * RowMapper class : 순수 jdbc 를 사용하던 시절, set~ rs~ 같은 로직을 repo 에 다 뿌려놓았는데,
     * jdbc template 사용시, 해당 과정을 Mapper class 로 따로 만들것을 요구한다.
     *
     * Mapper class 는 RowMapper 를 구현하는데, RowMapper 를 사용하면 원하는 형태의 결과값을 반환할수있다.
     * 즉, 우리가 db 로 보낸 쿼리의 결과가 담겨있는 ResultSet 을, java 형태로 받으려면 RowMapper 가 필요한 것이다.
     * ++ ResultSet 이란 SELECT 문을 실행하여 테이블로부터 얻은 결과를 저장하고있는 저장소라고 알면 되겠다.
     *
     * mapRow 를 오버라이딩 해서 구현하며, rs 에서 쿼리를 가져온 다음, member 에 넣는 역할을 한다.
     *
     * */
}
