package com.skillshare.platform.service;

import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepo;

    public CustomOAuth2UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(request);
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = (String) attributes.get("email");
        String name  = (String) attributes.get("name");

        // Check if user exists
        User user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User(email, name, "GOOGLE");
                    return userRepo.save(newUser);
                });

        return oauthUser;  // you can wrap this in your own OAuth2User implementation if you need roles, etc.
    }
}