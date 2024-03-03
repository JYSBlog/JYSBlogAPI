package com.blog.jysblog.jwt;

import com.blog.jysblog.config.SecurityConfig;
import com.blog.jysblog.jwt.dto.JwtResult;
import com.blog.jysblog.jwt.dto.UserTokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;
    private List<AntPathRequestMatcher> aprm;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.aprm = new ArrayList<>();
        for(String url: SecurityConfig.PERMIT_ALL) {
            aprm.add(new AntPathRequestMatcher(url));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(aprm.stream().noneMatch(a -> {
            log.trace("{} - {} : {}", a.getPattern(), request.getRequestURI(), a.matches(request));
            return a.matches(request);
        })) {
            String jws = getJwtFromRequest(request);
            JwtResult result = jwtTokenProvider.validate(jws, null);
            if(result.isValid()) {
                UserTokenInfo ui = result.getDmpUser();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(ui, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return bearerToken;
    }
}