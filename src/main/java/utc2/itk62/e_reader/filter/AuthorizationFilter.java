package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.repository.PermissionRepository;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PermissionRepository permissionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenPayload payload = (TokenPayload) authentication.getPrincipal();
//        if (!permissionRepository.existsPermission(payload.getUserId(), httpMethod, requestURI)) {
//            log.error("URI: {}. Method: {}", requestURI, httpMethod);
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "message goes here");
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
