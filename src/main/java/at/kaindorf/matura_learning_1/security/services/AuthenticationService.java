package at.kaindorf.matura_learning_1.security.services;

import at.kaindorf.matura_learning_1.security.dto.AuthenticationTokenResponse;
import at.kaindorf.matura_learning_1.security.dto.SignInRequest;
import at.kaindorf.matura_learning_1.security.dto.SignInResponse;
import at.kaindorf.matura_learning_1.security.dto.SignUpRequest;
import at.kaindorf.matura_learning_1.security.pojos.OtpToken;
import at.kaindorf.matura_learning_1.security.pojos.Role;
import at.kaindorf.matura_learning_1.security.pojos.User;
import at.kaindorf.matura_learning_1.security.repo.OtpRepository;
import at.kaindorf.matura_learning_1.security.repo.UserRepository;
import at.kaindorf.matura_learning_1.security.jwt.JwtService;
import at.kaindorf.matura_learning_1.security.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * AuthenticationService - 10.05.2025
 *
 * @author david
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;

    public AuthenticationTokenResponse signup(SignUpRequest signUpRequest){
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();
        try{
            userRepository.save(user);
            return new AuthenticationTokenResponse(jwtService.generateToken(user));
        }catch (Exception exception){
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public SignInResponse signin(SignInRequest signInRequest) {
        log.info("sign in");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        log.info("generate otp token");
        // generate OTP token
        User user = (User) authentication.getPrincipal();
        otpRepository.deleteAllByUser(userRepository.findByUsername(user.getUsername()).get());
        OtpToken otpToken = OtpToken.builder()
                .otpCode(new Random().nextInt(999999-100000) + 100000)
                .user((User) authentication.getPrincipal()).build();

        otpToken = otpRepository.save(otpToken);
        log.info(otpToken.toString());
        String token = jwtService.generateMfaToken((UserDetails) authentication.getPrincipal());
        log.info(token);

        return SignInResponse.builder().mfaToken(token).otpToken(otpToken.getOtpCode()).build();
    }

}
