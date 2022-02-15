package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.auth.JwtRequestFilter;
import daangnmarket.daangn.project.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtMemberDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Member member = memberService.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(), new ArrayList<>());
    }
}
