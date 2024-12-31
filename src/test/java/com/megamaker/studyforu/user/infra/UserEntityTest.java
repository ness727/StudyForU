package com.megamaker.studyforu.user.infra;

import com.megamaker.studyforu.user.domain.User;
import com.megamaker.studyforu.user.domain.vo.Provider;
import com.megamaker.studyforu.user.domain.vo.Role;
import com.megamaker.studyforu.web.securityconfig.ProviderEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {
    @DisplayName("엔티티 객체를 도메인 객체로 변환 성공")
    @Test
    void toModel() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(23L)
                .provider(ProviderEnum.GITHUB)
                .providerId("65363426")
                .providerNickname("Kim")
                .username("k123")
                .role(Role.USER)
                .point(27)
                .build();

        // when
        User user = userEntity.toModel();

        // then
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getProvider().getProvider()).isEqualTo(userEntity.getProvider());
        assertThat(user.getProvider().getProviderId()).isEqualTo(userEntity.getProviderId());
        assertThat(user.getProvider().getProviderNickname()).isEqualTo(userEntity.getProviderNickname());
        assertThat(user.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(user.getRole()).isEqualTo(userEntity.getRole());
        assertThat(user.getPoint()).isEqualTo(userEntity.getPoint());
    }

    @DisplayName("도메인 객체를 엔티티 객체로 변환 성공")
    @Test
    void toEntity() {
        // given
        Provider provider = new Provider(ProviderEnum.GITHUB, "43266524", "Kim");
        User user = User.builder()
                .id(23L)
                .provider(provider)
                .username("k123")
                .role(Role.USER)
                .point(27)
                .build();

        // when
        UserEntity userEntity = UserEntity.from(user);

        // then
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getProvider().getProvider()).isEqualTo(userEntity.getProvider());
        assertThat(user.getProvider().getProviderId()).isEqualTo(userEntity.getProviderId());
        assertThat(user.getProvider().getProviderNickname()).isEqualTo(userEntity.getProviderNickname());
        assertThat(user.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(user.getRole()).isEqualTo(userEntity.getRole());
        assertThat(user.getPoint()).isEqualTo(userEntity.getPoint());
    }

}