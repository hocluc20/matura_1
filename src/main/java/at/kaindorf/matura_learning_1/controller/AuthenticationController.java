package at.kaindorf.matura_learning_1.controller;

import at.kaindorf.matura_learning_1.dto.AuthenticationTokenResponse;
import at.kaindorf.matura_learning_1.dto.SignUpRequest;
import at.kaindorf.matura_learning_1.dto.SigninRequest;
import at.kaindorf.matura_learning_1.pojos.SigninResponse;
import at.kaindorf.matura_learning_1.pojos.User;
import at.kaindorf.matura_learning_1.security.JwtService;
import at.kaindorf.matura_learning_1.services.AuthenticationService;
import at.kaindorf.matura_learning_1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.controller
 **/


@RestController // Controller mit einem Mapping drin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signupRequest) {
        authenticationService.signup(signupRequest);
        return ResponseEntity.ok("Signed up!");
    }
    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @GetMapping("/otp-signin")
    public ResponseEntity<AuthenticationTokenResponse> otpSignin(@RequestHeader(name="Authorization") String mfaToken){
        String username = jwtService.extractUsername(mfaToken.substring(7));
        UserDetails user = userService.userDetailsService().loadUserByUsername(username);
        return ResponseEntity.ok(new AuthenticationTokenResponse(jwtService.generateToken(user)));
    }
}