package com.megamaker.studyforu.user.domain;

import com.megamaker.studyforu.common.infra.BaseDateTime;
import com.megamaker.studyforu.user.domain.vo.Provider;
import com.megamaker.studyforu.user.domain.vo.Role;
import com.megamaker.studyforu.web.securityconfig.ProviderEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    private Long id;

    private Provider provider;

    private String username;

    private Role role;
}