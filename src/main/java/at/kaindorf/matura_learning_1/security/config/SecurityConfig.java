package at.kaindorf.matura_learning_1.security.config;

import at.kaindorf.matura_learning_1.security.jwt.JwtAuthenticationFilter;
import at.kaindorf.matura_learning_1.security.exceptions.JwtUnauthorizedEndpoint;
import at.kaindorf.matura_learning_1.security.jwt.MfaAuthenticationFilter;
import at.kaindorf.matura_learning_1.security.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig - 10.05.2025
 *
 * @author david
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUnauthorizedEndpoint jwtUnauthorizedEndpoint;
    private final MfaAuthenticationFilter mfaAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/api/auth/otp-signin").authenticated()
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex->ex.authenticationEntryPoint(jwtUnauthorizedEndpoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(mfaAuthenticationFilter, jwtAuthenticationFilter.getClass());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new UserAuthenticationProvider();
    }

}