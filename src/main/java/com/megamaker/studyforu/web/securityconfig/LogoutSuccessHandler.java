package com.megamaker.studyforu.web.securityconfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final Environment environment;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri;
        String referer = request.getHeader("Referer");

//        HttpSession session = request.getSession();
//        session.invalidate();

        if (StringUtils.hasText(referer)
                && (referer.contains("localhost") || referer.contains("127.0.0.1"))) {
            redirectUri = environment.getProperty("dev.logout.success-uri");
        } else {
            redirectUri = environment.getProperty("prod.logout.success-uri");
        }
        response.sendRedirect(redirectUri);
    }
}
