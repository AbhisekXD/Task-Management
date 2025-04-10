package com.dac.util;

import org.springframework.security.core.context.SecurityContextHolder;
import com.dac.config.UserInfoConfig;
import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID getLoggedInUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserInfoConfig) {
            return ((UserInfoConfig) principal).getId();
        } else if (principal instanceof String) {
            try {
                return UUID.fromString((String) principal);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Principal cannot be converted to UUID: " + principal);
            }
        } else {
            throw new IllegalStateException("Principal type not recognized: " + principal.getClass());
        }
    }
}
