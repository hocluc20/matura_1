package at.kaindorf.matura_learning_1.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class JwtUnauthorizedEndpoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // default exception
        String error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        String msg = authException.getMessage();
        String path = String.valueOf(request.getRequestURL());

        if(authException instanceof BadCredentialsException){
            error = HttpStatus.CONFLICT.getReasonPhrase();
            status = HttpStatus.CONFLICT.value();
        }

        if(authException instanceof UserAlreadyExistsException){
            error = HttpStatus.UNAUTHORIZED.getReasonPhrase();
            status = HttpStatus.UNAUTHORIZED.value();
        }

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("path", path);
        responseBody.put("status", status);
        responseBody.put("msg", msg);
        responseBody.put("error", error);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper om = new ObjectMapper();
        om.writeValue(response.getWriter(), responseBody);
    }
}
