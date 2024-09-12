package uz.ccrew.flightmanagement.security.jwt;

import uz.ccrew.flightmanagement.security.user.UserDetailsImpl;
import uz.ccrew.flightmanagement.security.user.UserDetailsServiceImpl;
import uz.ccrew.flightmanagement.exp.unauthorized.TokenExpiredException;

import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver exceptionResolver;
    private final JWTService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = bearerToken.substring(7);

            if (jwtService.isTokenExpired(token)) {
                exceptionResolver.resolveException(request, response, null, new TokenExpiredException(jwtService.getTokenExpiredMessage(token)));
                return;
            }

            String login;
            if (request.getRequestURI().equals("/api/v1/auth/refresh")) {
                login = jwtService.extractRefreshTokenLogin(token);
            } else {
                login = jwtService.extractAccessTokenLogin(token);
            }

            if (login == null || SecurityContextHolder.getContext() == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login);
            LocalDateTime modifiedDate = userDetails.getUser().getCredentialsModifiedDate();
            modifiedDate = modifiedDate.minusNanos(modifiedDate.getNano() + 1);

            if (!modifiedDate.isBefore(jwtService.getGeneratedTime(token))) {
                exceptionResolver.resolveException(request, response, null, new BadCredentialsException("Bad credentials"));
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, new BadCredentialsException("Bad credentials"));
        }
    }
}
