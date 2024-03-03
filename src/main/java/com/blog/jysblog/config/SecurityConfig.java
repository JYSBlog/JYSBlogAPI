package com.blog.jysblog.config;

import com.blog.jysblog.jwt.CustomAuthenticationEntryPoint;
import com.blog.jysblog.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public static final String[] PERMIT_ALL = {
        "/api-docs/**", "/swagger-ui**", "/swagger-ui/**", "/login", "/api/login", "/api/join"
    };

    public static final String[] AUTH_ALL = {
        "/api/**", "api"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
            ))
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers(PERMIT_ALL).permitAll()
                    .requestMatchers(AUTH_ALL).authenticated()
                    .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling((exception) -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }


}
