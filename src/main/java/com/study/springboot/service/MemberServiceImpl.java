package com.study.springboot.service;

import com.study.springboot.domain.Member;
import com.study.springboot.domain.MemberAuth;
import com.study.springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public void register(Member member) throws Exception {
        Member memberEntity = new Member();

        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        MemberAuth auth = new MemberAuth();
        auth.setAuth("ROLE_MEMBER");

        memberEntity.addAuth(auth);

        repository.save(memberEntity);

        member.setUserNo(memberEntity.getUserNo());

    }

    @Override
    public List<Member> list() throws Exception {
        List<Object[]> valueArrays = repository.listAllMember();

        List<Member> memberList = new ArrayList<Member>();
        for (Object[] valueArray : valueArrays) {
            Member member = new Member();

            member.setUserNo((Long) valueArray[0]);
            member.setUserId((String) valueArray[1]);
            member.setUserPw((String) valueArray[2]);
            member.setUserName((String) valueArray[3]);
            member.setJob((String) valueArray[4]);
            member.setCoin((int) valueArray[5]);
            member.setRegDate((LocalDateTime) valueArray[6]);

            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public Member read(Long userNo) throws Exception {
        return repository.getReferenceById(userNo);
    }

    @Override
    public void modify(Member member) throws Exception {

        Member memberEntity = repository.getReferenceById(member.getUserNo());
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        List<MemberAuth> memberAuthList = memberEntity.getAuthList();
        List<MemberAuth> authList = member.getAuthList();

        for (int i = 0; i < authList.size(); i++) {

            MemberAuth auth = authList.get(i);

            if (i < memberAuthList.size()) {
                MemberAuth memberAuth = memberAuthList.get(i);
                memberAuth.setAuth(auth.getAuth());
            }

        }

        repository.save(memberEntity);
    }

    @Override
    public void remove(Long userNo) throws Exception {
        repository.deleteById(userNo);
    }
}
