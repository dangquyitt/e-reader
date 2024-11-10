package utc2.itk62.e_reader.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.model.AuthenticationToken;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.TokenException;
import utc2.itk62.e_reader.service.TokenService;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final Translator translator;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("accessToken")) {
                        token = cookie.getValue();
                    }
                }
            }

            if (token == null) {
                throw new TokenException("Token is empty");
            }

            TokenPayload payload = tokenService.validateAccessToken(token);
            AuthenticationToken authentication = new AuthenticationToken(payload);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            log.error("TokenException | {}", e.getMessage());
            HTTPResponse httpResponse = HTTPResponse.builder().message(translator.translate(MessageCode.ERROR_UNAUTHORIZED)).build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(httpResponse));
        }
    }
}
