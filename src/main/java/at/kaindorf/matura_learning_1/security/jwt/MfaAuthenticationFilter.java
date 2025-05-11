package at.kaindorf.matura_learning_1.security.jwt;

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
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.info("authHeader missing (mfa)");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        if(!jwtService.isMfaTokenValid(token, userDetails)){
            log.info("mfa token is invalid");
            filterChain.doFilter(request, response);
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();
        Optional<OtpToken> optionalOtpToken = otpRepository.findOtpTokenByUser_Username(user.getUsername());
        if(optionalOtpToken.isEmpty()){
            log.info("user has no opt token issued");
            filterChain.doFilter(request, response);
            return;
        }

        OtpToken otpToken = optionalOtpToken.get();
        String otpHeader = request.getHeader("OtpCode");
        if(otpHeader == null){
            log.info("OtpHeader is null");
            filterChain.doFilter(request, response);
            return;
        }

        Integer codeInHeader = Integer.parseInt(otpHeader);
        if(!codeInHeader.equals(otpToken.getOtpCode())){
            log.info("OTP Code invalid");
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.info("mfa authenticated");
        filterChain.doFilter(request, response);
    }
}
