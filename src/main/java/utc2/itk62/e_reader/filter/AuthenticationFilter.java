package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.model.AuthenticationToken;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.UnauthorizedException;
import utc2.itk62.e_reader.service.TokenService;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        if(request.getCookies() != null){
            for (Cookie cookie: request.getCookies()){
                if (cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        if (token != null ){
            try {
                TokenPayload payload = tokenService.validateAccessToken(token);
                AuthenticationToken authentication = new AuthenticationToken(payload);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e){
                throw new UnauthorizedException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }
}
