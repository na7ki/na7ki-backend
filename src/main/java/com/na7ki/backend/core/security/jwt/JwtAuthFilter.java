package com.na7ki.backend.core.security.jwt;

import com.na7ki.backend.core.security.exception.InvalidJwtTokenException;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.exception.other.AccountNotActiveException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // prevents other authentication mechanisms from running at the same time per request
        // but for every request, the context resets to null. authentication context data don't get preserved over requests (stateless)
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Design decision: DB hit on every request.
                // Ensures the user still exists and is active, and keeps roles/permissions always fresh — at the cost of one indexed lookup per request
                // Acceptable at current scale. Revisit with a cache if this becomes a bottleneck.
                Claims claims = jwtUtil.extractClaims(token);
                Long userId = Long.valueOf(claims.getSubject());

                User user = userService.findByIdOrThrow(userId);

                if (!user.getIsActive()) {
                    throw new AccountNotActiveException("Account is inactive");
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (JwtException | NumberFormatException e) {
                throw new InvalidJwtTokenException("Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
