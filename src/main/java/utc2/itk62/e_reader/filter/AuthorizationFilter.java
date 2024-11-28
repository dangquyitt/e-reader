package utc2.itk62.e_reader.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utc2.itk62.e_reader.core.response.HTTPResponse;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        request.getPathInfo();

        // /books/123
        // TODO: authorization
        // Get -> user -> userId
        // path match /books/123


        filterChain.doFilter(request, response);
    }
}
