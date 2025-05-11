package at.kaindorf.matura_learning_1.security.config;

import at.kaindorf.matura_learning_1.security.services.UserService;
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
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. userdaten holen
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 2. gibts den user?
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        // 3. credentials abfragen
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Foisches passwort oida");
        }

        // 4. username passwort authentication object

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
