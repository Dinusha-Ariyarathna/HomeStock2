package com.skillshare.platform.config;


import com.skillshare.platform.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;

    public SecurityConfig(CustomOAuth2UserService oauth2UserService) {
        this.oauth2UserService = oauth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ping", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("http://localhost:3000")        // React app entry
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauth2UserService)
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("http://localhost:3000/login")
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}