package daangnmarket.daangn.project.auth;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.service.CustomUserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ClientMemberLoader {
    public Member getClientMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetailsImpl) {
            return ((CustomUserDetailsImpl) authentication.getPrincipal()).getMember();
        }
        return null;
    }
}
