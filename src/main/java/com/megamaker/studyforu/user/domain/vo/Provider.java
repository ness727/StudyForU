package com.megamaker.studyforu.user.domain.vo;

import com.megamaker.studyforu.web.securityconfig.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Provider {
    private ProviderEnum provider;

    private String providerId;

    private String providerNickname;
}
