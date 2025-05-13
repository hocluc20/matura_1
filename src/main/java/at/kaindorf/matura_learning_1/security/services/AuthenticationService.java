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
import org.springframework.dao.DataIntegrityViolationException;
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
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String signup(SignUpRequest signUpRequest){
        User user = User.builder()
                .enabled(true)
                .role(Role.USER)
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        try{
            userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new UserAlreadyExistsException("User already exists");
        }

        return "Himmellecken, raphi inluencer, neie collaboration euda";
    }

    public SignInResponse signin(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        User user = (User) authentication.getPrincipal();

        OtpToken otpToken = OtpToken.builder()
                .user(user)
                .otpCode(new Random().nextInt(999999-100000)+100000)
                .build();
        otpRepository.save(otpToken);

        String token = jwtService.generateMfaToken((UserDetails) authentication.getPrincipal());

        return SignInResponse.builder().otpToken(otpToken.getOtpCode()).mfaToken(token).build();
    }

}
