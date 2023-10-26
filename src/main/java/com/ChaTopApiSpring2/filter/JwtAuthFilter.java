package com.ChaTopApiSpring2.filter;

import com.ChaTopApiSpring2.exceptions.AccountException;
import com.ChaTopApiSpring2.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filter to handle JWT (JSON Web Token) authentication.
 * This filter intercepts HTTP requests and verifies the presence and validity
 * of a JWT in the "Authorization" header.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    /**
     * Handles the filtering logic for incoming HTTP requests.
     *
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @param filterChain The filter chain that this filter is part of.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader("Authorization");

        if (headerToken == null || headerToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = jwtService.extractTokenFromHeader(headerToken);
            String email = jwtService.extractEmail(token);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (AccountException exception) {
            throw new AccountException("Bad token");
        }
    }
}
