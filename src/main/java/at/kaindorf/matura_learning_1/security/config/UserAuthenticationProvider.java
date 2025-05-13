package at.kaindorf.matura_learning_1.security.config;

import at.kaindorf.matura_learning_1.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserAuthenticationProvider - 10.05.2025
 *
 * @author david
 */

@Service
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 1 daten holen
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // validieren
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Wrong password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
