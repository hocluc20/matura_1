package at.kaindorf.matura_learning_1.services;

import at.kaindorf.matura_learning_1.dto.AuthenticationTokenResponse;
import at.kaindorf.matura_learning_1.dto.SignUpRequest;
import at.kaindorf.matura_learning_1.dto.SigninRequest;
import at.kaindorf.matura_learning_1.pojos.OTPToken;
import at.kaindorf.matura_learning_1.pojos.Role;
import at.kaindorf.matura_learning_1.pojos.SigninResponse;
import at.kaindorf.matura_learning_1.pojos.User;
import at.kaindorf.matura_learning_1.repos.OTPRepository;
import at.kaindorf.matura_learning_1.repos.UserRepository;
import at.kaindorf.matura_learning_1.security.JwtService;
import at.kaindorf.matura_learning_1.security.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.services
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OTPRepository otpRepository;

    public AuthenticationTokenResponse signup(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        try {
            userRepository.save(user);

            return new AuthenticationTokenResponse(jwtService.generateToken(user));
        }catch (Exception e){
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public SigninResponse signin(SigninRequest signinRequest){
        log.info("IM SIGNIN");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),
                        signinRequest.getPassword()));
        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        String mfaToken = jwtService.generateMfaToken((UserDetails) authentication.getPrincipal());
        int random = new Random().nextInt(999999-100000+1)+100000;
        OTPToken otpToken = OTPToken.builder()
                .otpCode(random)
                .user((User) authentication.getPrincipal())
                .build();
        otpRepository.save(otpToken);
        return SigninResponse.builder().otp(random).mfa(mfaToken).build();
        //return new AuthenticationTokenResponse(token);
    }
}
