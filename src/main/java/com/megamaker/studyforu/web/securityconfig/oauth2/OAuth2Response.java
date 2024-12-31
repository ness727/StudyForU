package com.megamaker.studyforu.web.securityconfig.oauth2;


import com.megamaker.studyforu.web.securityconfig.ProviderEnum;

public interface OAuth2Response {
    ProviderEnum getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
