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

public class AuthenticationController {

}
