package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // /books/123
        request.getPathInfo();
        request.getPathInfo();
        request.getMethod();
        // TODO: authorization
        // Get -> user -> userId
        // path match /books/123


        filterChain.doFilter(request, response);
    }
}
