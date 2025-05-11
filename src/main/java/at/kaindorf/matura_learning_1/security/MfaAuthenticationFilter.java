package at.kaindorf.matura_learning_1.security;

import at.kaindorf.matura_learning_1.pojos.OTPToken;
import at.kaindorf.matura_learning_1.repos.OTPRepository;
import at.kaindorf.matura_learning_1.services.UserService;
import io.jsonwebtoken.Jwt;
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
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.security
 **/

@Component
@Slf4j
@RequiredArgsConstructor
public class MfaAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final OTPRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            log.info("header invalid");
            filterChain.doFilter(request, response);
            return;
        }



        String token = header.substring(7);
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        log.info("Afer getting the token ");

        try{
            if(!jwtService.isMfaTokenValid(token, userDetails)){
                filterChain.doFilter(request, response);
                return;
            }

            int mfa = Integer.parseInt(request.getHeader("OtpCode"));

            Optional<OTPToken> otpToken = otpRepository.findOTPTokenByUserUsername(username);
            log.info("After searching the otp in db");
            if(mfa != otpToken.get().getOtpCode()){
                filterChain.doFilter(request, response);
                return;
            }
            // 4. Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            log.info("Security Context updated");
        }catch (Exception e){
            throw new UserAlreadyExistsException(e.getMessage());
        }

        filterChain.doFilter(request,response);
    }
}
