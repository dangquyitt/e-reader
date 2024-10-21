package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.model.AuthenticationToken;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.service.TokenService;

import java.io.IOException;

@Component
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public CustomFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = null;
        if(request.getCookies() != null){
            for (Cookie cookie: request.getCookies()){
                if (cookie.getName().equals("accessToken")){
                    bearerToken = cookie.getValue();
                    log.info("Get token from cookie: "+ cookie.getValue());
                }
            }
        }
        String bearerPrefix = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(bearerPrefix)){
            String token = bearerToken.substring(bearerPrefix.length());
            try {
                TokenPayload payload = tokenService.validateAccessToken(token);

                AuthenticationToken authentication = new AuthenticationToken(payload);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e){
                throw new CustomException().setException(e).addError(new Error("token","token.access_token.invalid"));
            }
        }

        filterChain.doFilter(request, response);
    }
}
