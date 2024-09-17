package uz.ccrew.flightmanagement.util;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.exp.unauthorized.Unauthorized;
import uz.ccrew.flightmanagement.security.user.UserDetailsImpl;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Component
public class AuthUtil {
    private User getLoggedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.isAuthenticated()) {
                return null;
            }
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUser();
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<User> takeLoggedUser() {
        return Optional.ofNullable(getLoggedUser());
    }

    public User loadLoggedUser() {
        Optional<User> optional = takeLoggedUser();
        if (optional.isEmpty()) {
            throw new Unauthorized();
        }
        return optional.get();
    }
}