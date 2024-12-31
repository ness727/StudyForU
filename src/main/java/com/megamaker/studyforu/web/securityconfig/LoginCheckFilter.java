package com.megamaker.studyforu.web.securityconfig;

import com.megamaker.studyforu.user.domain.User;
import com.megamaker.studyforu.user.domain.exception.UserNotFoundException;
import com.megamaker.studyforu.user.domain.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter extends OncePerRequestFilter {
    private final String[] permitPathArr;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String sessionId = request.getHeader("SESSIONID");
//        if (sessionId == null) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.setContentType("application/json; charset=UTF-8");
//            response.getWriter().write("{\"message\": \"토큰 헤더 정보 불일치\"}");
//            return;
//        }

        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");

            // 회원 조회
            User foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

            Authentication auth = new UsernamePasswordAuthenticationToken(  // 새 유저 인증 객체 생성
                    foundUser,
                    null,
                    List.of(new SimpleGrantedAuthority(foundUser.getRole().name()))
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            doFilter(request, response, filterChain);
        } catch (RuntimeException e) {
            log.error("유저 인증 오류", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"message\": \"토큰 파싱 에러\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        for (String path : permitPathArr) {
            if (request.getServletPath().equals(path)) {
                return true;
            }
        }
        return false;
    }
}
