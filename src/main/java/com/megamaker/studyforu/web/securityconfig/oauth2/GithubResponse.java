package com.megamaker.studyforu.web.securityconfig.oauth2;

import com.megamaker.studyforu.web.securityconfig.ProviderEnum;

import java.util.Map;

public class GithubResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public GithubResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.GITHUB;
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("login").toString();
    }
}
