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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final JwtUnauthorizedEndpoint jwtUnauthorizedEndpoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. get Authorization header
        String header = request.getHeader("Authorization");

        if(header == null ||!header.startsWith("Bearer ")){
            log.info("No authorization or missing bearer");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. jwt aus dem header holen
        String token = header.substring(7);

        try{
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(jwtService.extractUsername(token));

            if(!jwtService.isTokenValid(token, userDetails)){
                log.info("Access token is not valid!");
                filterChain.doFilter(request, response);
                return;
            }

            // security context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            log.info("security context updated");

        } catch (UsernameNotFoundException e){
            log.info("Username not found");
            jwtUnauthorizedEndpoint.commence(request, response, e);
        }

        log.info("JWT Access filter done");
        filterChain.doFilter(request, response);
    }
}
