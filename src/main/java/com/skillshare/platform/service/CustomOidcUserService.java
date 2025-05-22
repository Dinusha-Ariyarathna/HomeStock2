package com.skillshare.platform.service;

import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {
    /**
     * Repository interface for managing {@link User} entities.
     * Used to perform CRUD operations and queries related to users in the database.
     *
     * This variable is a dependency injected through the constructor and can be
     * utilized to access or modify user records in persistence layers.
     */
    private final UserRepository userRepo;

    /**
     * Creates an instance of {@code CustomOidcUserService}.
     *
     * @param userRepo the {@code UserRepository} used to interact with the user data store
     */
    public CustomOidcUserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Loads and processes an OpenID Connect user based on the provided request.
     * Delegates to the parent implementation to load the user, extracts user attributes,
     * and persists the user in the repository if not already present.
     *
     * @param request the user request containing details required to load the user
     * @return the loaded OIDC user
     * @throws OAuth2AuthenticationException if an error occurs during authentication
     */
    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        // Delegate to the default implementation for loading a user
        OidcUser oidcUser = super.loadUser(request);

        // Extract the attributes
        String email = oidcUser.getAttribute("email");
        String name  = oidcUser.getAttribute("name");

        // Save the user if not already present
        userRepo.findByEmail(email)
                .orElseGet(() -> userRepo.save(new User(email, name, "GOOGLE")));

        return oidcUser;
    }
}
