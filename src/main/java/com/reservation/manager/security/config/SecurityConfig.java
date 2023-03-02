package com.reservation.manager.security.config;

import com.reservation.manager.security.filter.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtRequestFilter jwtRequestFilter;
    private final LogoutHandler logoutHandler;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.debug("SecurityConfig initialized.");
        http
                .authorizeHttpRequests(
                        (auth)-> auth.requestMatchers(HttpMethod.GET,
                                        "/swagger-ui-custom/",
                                        "/api/swagger-ui/**",
                                        "/api/docs/**",
                                        "/api/docs-ui",
                                        "/v3/api-docs/",
                                        "/h2-console").permitAll()
                                .requestMatchers(HttpMethod.POST,
                                        "/auth/login",
                                        "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.PATCH,
                                        "/confirm").hasRole("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )
                .csrf().disable()
                .formLogin()
                .and().authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/auth/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(
                        (request, response, authentication) -> SecurityContextHolder.clearContext()
                )
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );
        //h2 config
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }
}
