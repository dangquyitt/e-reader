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
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.repository.PermissionRepository;
import utc2.itk62.e_reader.repository.SubscriptionRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PermissionRepository permissionRepository;
    private final SubscriptionRepository subscriptionRepository;

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
        if (requestURI.matches("/api/books/\\d+/fileURL$")) {
            Optional<Subscription> subscriptionOp = subscriptionRepository.findByLatestEndTimeAndUserId(payload.getUserId());
            if (subscriptionOp.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                return;
            }
            Subscription subscription = subscriptionOp.get();
            if (subscription.getEndTime().isBefore(Instant.now())) {
                response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

//        if (!permissionRepository.existsPermission(payload.getUserId(), httpMethod, requestURI)) {
//            log.error("URI: {}. Method: {}", requestURI, httpMethod);
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "message goes here");
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
