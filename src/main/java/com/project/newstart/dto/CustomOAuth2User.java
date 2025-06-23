package com.project.newstart.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, UserDetails { // 변경된 부분

    private final OAuth2Response oAuth2Response;
    private final String role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> role);
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    @Override
    public String getUsername() { // 변경된 부분
        return oAuth2Response.getEmail();
    }

    @Override
    public String getPassword() { // 변경된 부분
        return null; // OAuth2 users may not have a password
    }

    @Override
    public boolean isAccountNonExpired() { // 변경된 부분
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 변경된 부분
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 변경된 부분
        return true;
    }

    @Override
    public boolean isEnabled() { // 변경된 부분
        return true;
    }

    public String getUserName() {
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }

    public String getEmail() {
        return oAuth2Response.getEmail();
    }

    public String getProvider() {
        return oAuth2Response.getProvider();
    }
}