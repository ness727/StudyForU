package com.megamaker.studyforu.web.securityconfig;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
public class CustomCorsConfig implements CorsConfigurationSource {
    private final String allowedFrontDev;
    private final String allowedFrontProd;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowedMethods(List.of("*"));
        corsConfig.setAllowedOrigins(List.of(allowedFrontDev, allowedFrontProd));
        return corsConfig;
    }
}
