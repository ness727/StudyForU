package com.megamaker.studyforu.web.securityconfig;

import com.megamaker.studyforu.web.securityconfig.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final Environment environment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        Long userId = ((CustomOAuth2User) authentication.getPrincipal()).getUserId();

        // 세션 저장소에 저장
        request.getSession().setAttribute("userId", userId);

        String redirectUri = environment.getProperty("custom.login.success-uri");
        response.sendRedirect(redirectUri);
    }
}
