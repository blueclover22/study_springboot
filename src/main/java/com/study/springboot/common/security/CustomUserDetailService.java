package com.study.springboot.common.security;

import com.study.springboot.domain.CustomUser;
import com.study.springboot.domain.Member;
import com.study.springboot.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("CustomUserDetailService.loadUserByUsername");

        List<Member> members = repository.findByUserId(username);
        if (members == null || members.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        Member member = members.get(0);
        return new CustomUser(member);
    }
}
