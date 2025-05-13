package at.kaindorf.matura_learning_1.security.controller;

import at.kaindorf.matura_learning_1.security.dto.AuthenticationTokenResponse;
import at.kaindorf.matura_learning_1.security.dto.SignInRequest;
import at.kaindorf.matura_learning_1.security.dto.SignInResponse;
import at.kaindorf.matura_learning_1.security.dto.SignUpRequest;
import at.kaindorf.matura_learning_1.security.jwt.JwtService;
import at.kaindorf.matura_learning_1.security.services.AuthenticationService;
import at.kaindorf.matura_learning_1.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * AuthenticationController - 10.05.2025
 *
 * @author david
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signup(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @GetMapping("/otp-signin")
    public ResponseEntity<AuthenticationTokenResponse> optSignin(@RequestHeader("Authorization") String header){

        String username = jwtService.extractUsername(header.substring(7));
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        String token = jwtService.generateMfaToken(userDetails);

        return ResponseEntity.ok(new AuthenticationTokenResponse(token));
    }

}
