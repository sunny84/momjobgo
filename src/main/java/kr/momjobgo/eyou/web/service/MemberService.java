package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAll();

//    List<Member> findAllMembersNative();
}
