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

@RestController // Controller mit einem Mapping drin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signupRequest) {
        authenticationService.signup(signupRequest);
        return ResponseEntity.ok("Successfully signed up!");
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @GetMapping("/otp-signin")
    public ResponseEntity<AuthenticationTokenResponse> optSignin(@RequestHeader(name = "Authorization") String authHeader){
        String username = jwtService.extractUsername(authHeader.substring(7));
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);
        log.info("otpsignin completed");
        return ResponseEntity.ok(new AuthenticationTokenResponse(token));
    }
}
