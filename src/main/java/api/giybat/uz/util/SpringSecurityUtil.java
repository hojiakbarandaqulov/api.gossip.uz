package api.giybat.uz.util;

import api.giybat.uz.config.CustomUserDetails;
import api.giybat.uz.entity.ProfileEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {
    public static CustomUserDetails getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user;
    }
    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Foydalanuvchi tizimga kirmagan.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof String s && s.equals("anonymousUser")) {
            throw new RuntimeException("Anonymous foydalanuvchi: token yo‘q yoki noto‘g‘ri.");
        }

        if (principal instanceof CustomUserDetails user) {
            return user.getId();
        }

        throw new RuntimeException("Noto‘g‘ri principal: " + principal.getClass().getName());
    }


}
