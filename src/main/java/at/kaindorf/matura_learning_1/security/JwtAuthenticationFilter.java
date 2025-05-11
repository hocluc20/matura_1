package at.kaindorf.matura_learning_1.security;

import at.kaindorf.matura_learning_1.services.UserService;
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
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.security
 **/

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtUnauthorizedEndpoint jwtUnauthorizedEndpoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Get Authorization Header
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")){
            log.info("No Authorization Header or missing Bearer");
            filterChain.doFilter(request,response);
            return;
        }

        // 2. token rausholen
        token = token.substring(7);

        // 3. Jwt token access is valid

        try {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(jwtService.extractUsername(token));

            if(!jwtService.isTokenValid(token, userDetails)){
                log.info("Invalid Access token");
                filterChain.doFilter(request, response);
                return;
            }

            // 4. Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            log.info("Security Context updated");

        }catch (UsernameNotFoundException e){
            log.info(e.getMessage());
            jwtUnauthorizedEndpoint.commence(request,response,e);
        }
        log.info("JWT Access filter done");
        filterChain.doFilter(request, response);
    }
}
