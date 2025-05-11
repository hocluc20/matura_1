package at.kaindorf.matura_learning_1.security;

import at.kaindorf.matura_learning_1.services.UserService;
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
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.security
 **/

@Service
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. Userdaten holen
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 2. Gibts n user??????
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        // 3. Passen die credentials
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Foisches Passwort du depp");
        }

        // 4. Username password authentication objekt returnen
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
