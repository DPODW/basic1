package coms.firstproject.domain.memberInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {
    private String name;

    private String id;

    private String pwd;

    private String call;


    /* 순서가 될 값 */

    public Member(){

    }

    public Member(String name, String id, String pwd, String call) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.call = call;
    }
}
