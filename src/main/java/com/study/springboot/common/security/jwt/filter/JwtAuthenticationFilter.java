package com.study.springboot.common.security.jwt.filter;

import com.study.springboot.common.security.jwt.constants.SecurityConstants;
import com.study.springboot.common.security.jwt.provider.JwtTokenProvider;
import com.study.springboot.domain.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUser user = (CustomUser) authResult.getPrincipal();
        long userNo = user.getUserNo();
        String userId = user.getUserId();

        List<String> roles = user.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .toList();

        String token = tokenProvider.createToken(userNo, userId, roles);

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }
}
