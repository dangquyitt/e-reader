package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.domain.model.AuthenticationToken;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.TokenException;
import utc2.itk62.e_reader.service.TokenService;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String ACCESS_TOKEN_KEY = "accessToken";
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractTokenFromCookies(request);

            if (token == null) {
                doFilter(request, response, filterChain);
                return;
            }

            TokenPayload payload = tokenService.validateAccessToken(token);
            AuthenticationToken authentication = new AuthenticationToken(payload);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        } catch (TokenException e) {
            log.error("TokenException | {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (ACCESS_TOKEN_KEY.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
