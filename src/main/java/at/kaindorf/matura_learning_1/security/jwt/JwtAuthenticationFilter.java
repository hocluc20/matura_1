package at.kaindorf.matura_learning_1.security.jwt;

import at.kaindorf.matura_learning_1.security.exceptions.JwtUnauthorizedEndpoint;
import at.kaindorf.matura_learning_1.security.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter - 11.05.2025
 *
 * @author david
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.info("Raphi hot kan kopf");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(jwtService.extractUsername(token));

        if(!jwtService.isTokenValid(token, userDetails)){
            log.info("raphi nix authenticated");
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.info("raphi hats gschafft");
        filterChain.doFilter(request, response);
    }
}
