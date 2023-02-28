package com.complejolapasionaria.reservation.security.config;

import com.complejolapasionaria.reservation.repository.ITokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final ITokenRepository iTokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        if (authorizationHeader ==null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }
        jwt = authorizationHeader.substring(7);
        var storedToken = iTokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            iTokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
