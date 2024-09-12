package uz.ccrew.flightmanagement.config;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.util.AuthUtil;

import org.springframework.stereotype.Component;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {
    private final AuthUtil authUtil;

    public AuditorAwareImpl(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Optional<User> optional = authUtil.takeLoggedUser();
        return optional.map(User::getId);
    }
}