package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.Member;
import kr.momjobgo.eyou.web.jpa.repository.MemberRepository;
import kr.momjobgo.eyou.web.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
//
//    @Override
//    public List<Member> findAllMembersNative() {
//        return memberRepository.findAllMembersNative();
//    }
}
