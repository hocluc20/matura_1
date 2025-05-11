package at.kaindorf.matura_learning_1.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUnauthorizedEndpoint - 11.05.2025
 *
 * @author david
 */

@Component
public class JwtUnauthorizedEndpoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public JwtUnauthorizedEndpoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // default error
        String error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        String msg = authException.getMessage();
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

        // custom errors
        if( authException instanceof UserAlreadyExistsException){
            error = HttpStatus.CONFLICT.getReasonPhrase();
            status = HttpServletResponse.SC_CONFLICT;
        }

        if (authException instanceof BadCredentialsException) {
            error = HttpStatus.UNAUTHORIZED.getReasonPhrase();
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }

        // response bauen
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("path", request.getRequestURL());
        responseBody.put("error", error);
        responseBody.put("message", msg);
        responseBody.put("status", status);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper om = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
