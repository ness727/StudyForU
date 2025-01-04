package com.megamaker.studyforu.web.securityconfig;

import com.megamaker.studyforu.user.domain.User;
import com.megamaker.studyforu.user.domain.UserRepository;
import com.megamaker.studyforu.user.domain.vo.Provider;
import com.megamaker.studyforu.user.domain.vo.Role;
import com.megamaker.studyforu.web.securityconfig.oauth2.CustomOAuth2User;
import com.megamaker.studyforu.web.securityconfig.oauth2.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = ProviderEnum.getOAuth2Response(oAuth2User, registrationId);

        Optional<User> foundUser = userRepository.findByProviderId(oAuth2Response.getProviderId());
        Role role;
        if (foundUser.isEmpty()) {
            Provider newProvider
                    = new Provider(oAuth2Response.getProvider(), oAuth2Response.getProviderId(), oAuth2Response.getName());

            User newUser = User.builder()
                    .provider(newProvider)
                    .username(newProvider.getProviderNickname())
                    .role(Role.USER)
                    .build();
            userRepository.save(newUser);
            return new CustomOAuth2User(oAuth2Response, Role.USER.name(), newUser.getId());
        } else {
            role = foundUser.get().getRole();
            return new CustomOAuth2User(oAuth2Response, role.name(), foundUser.get().getId());
        }
    }
}
