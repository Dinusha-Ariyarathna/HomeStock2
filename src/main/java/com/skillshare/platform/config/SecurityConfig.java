package com.skillshare.platform.config;


import com.skillshare.platform.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final CustomOidcUserService oidcUserService;

    public SecurityConfig(CustomOidcUserService oidcUserService) {
        this.oidcUserService = oidcUserService;
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
                        .defaultSuccessUrl("http://localhost:3000", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                // Use our OIDC service to both load and save users
                                .oidcUserService(oidcUserService)
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("http://localhost:3000/login")
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    /**
     * Configures and provides the CORS (Cross-Origin Resource Sharing) settings for the application.
     * Specifies the allowed origins, HTTP methods, headers, and credentials settings for CORS requests.
     *
     * @return a {@code CorsConfigurationSource} instance containing the defined CORS policies
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);        // important for cookies
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}