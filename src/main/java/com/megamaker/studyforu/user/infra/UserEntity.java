package com.megamaker.studyforu.user.infra;

import com.megamaker.studyforu.common.infra.BaseDateTime;
import com.megamaker.studyforu.user.domain.User;
import com.megamaker.studyforu.user.domain.vo.Provider;
import com.megamaker.studyforu.user.domain.vo.Role;
import com.megamaker.studyforu.web.securityconfig.ProviderEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class UserEntity extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider_nickname")
    private String providerNickname;

    private String username;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    private Integer point;

    public User toModel() {
        Provider userProvider = new Provider(provider, providerId, providerNickname);
        return User.builder()
                .id(id)
                .username(username)
                .provider(userProvider)
                .role(role)
                .point(point)
                .build();
    }

    public static UserEntity from(User user) {
        Provider userProvider = user.getProvider();
        return UserEntity.builder()
                .id(user.getId())
                .provider(userProvider.getProvider())
                .providerId(userProvider.getProviderId())
                .providerNickname(userProvider.getProviderNickname())
                .username(user.getUsername())
                .role(user.getRole())
                .point(user.getPoint())
                .build();
    }
}