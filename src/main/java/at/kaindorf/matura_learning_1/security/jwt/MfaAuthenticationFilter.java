package at.kaindorf.matura_learning_1.security.jwt;

import at.kaindorf.matura_learning_1.security.exceptions.UserAlreadyExistsException;
import at.kaindorf.matura_learning_1.security.pojos.OtpToken;
import at.kaindorf.matura_learning_1.security.pojos.User;
import at.kaindorf.matura_learning_1.security.repo.OtpRepository;
import at.kaindorf.matura_learning_1.security.repo.UserRepository;
import at.kaindorf.matura_learning_1.security.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * MfaAuthenticationFilter - 11.05.2025
 *
 * @author david
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class MfaAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;
    private final OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            log.info("Header behindert");
            filterChain.doFilter(request,response);
            return;
        }

        token = token.substring(7);

        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        try{
            if(!jwtService.isMfaTokenValid(token, userDetails)){
                log.info("MFA is hin");
                filterChain.doFilter(request,response);
                return;
            }
            Optional<OtpToken> otpToken = otpRepository.findOtpTokenByUser_Username(username);

            Integer mfa = Integer.parseInt(request.getHeader("OtpCode"));

            log.info(mfa.toString());
            log.info(otpToken.toString());

            if(!mfa.equals(otpToken.get().getOtpCode()) ){
                filterChain.doFilter(request,response);
                log.info("OIS OASCH ");
                return;
            }
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            log.info("Security Context updated");
        }catch (Exception e){
            throw new UserAlreadyExistsException(e.getMessage());
        }
        log.info("mfa done");
        filterChain.doFilter(request,response);
    }
}
