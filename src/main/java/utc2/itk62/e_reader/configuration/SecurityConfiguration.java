package utc2.itk62.e_reader.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import utc2.itk62.e_reader.filter.AuthenticationFilter;
import utc2.itk62.e_reader.filter.AuthorizationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] PUBLIC_ENDPOINTS = {"/api/auth/**"};
    private final AuthenticationFilter authenticationFilter;
    private final AuthorizationFilter authorizationFilter;

    public SecurityConfiguration(AuthenticationFilter authenticationFilter, AuthorizationFilter authorizationFilter) {
        this.authenticationFilter = authenticationFilter;
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
