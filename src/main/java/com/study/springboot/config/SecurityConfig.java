package com.study.springboot.config;

import com.study.springboot.common.security.CustomAccessDeniedHandler;
import com.study.springboot.common.security.CustomUserDetailService;
import com.study.springboot.common.security.RestAuthenticationEntryPoint;
import com.study.springboot.common.security.jwt.filter.JwtAuthenticationFilter;
import com.study.springboot.common.security.jwt.filter.JwtRequestFilter;
import com.study.springboot.common.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration, MessageSource messageSource) throws Exception {

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/").permitAll() //
                .requestMatchers("/login").permitAll() //
                .requestMatchers("/codes/**").permitAll() // 코드 관련 API 허용
                .requestMatchers("/users/**").permitAll() // 회원 관련 API 허용
                .requestMatchers("/codeGroups/**").hasRole("ADMIN") // 코드그룹은 관리자만
                .requestMatchers("/codeDetails/**").hasRole("ADMIN") // 코드그룹은 관리자만
                .requestMatchers(request ->
                    request.getRequestURI().startsWith("/boards") && "GET".equals(request.getMethod())
                ).permitAll()
                .requestMatchers("/boards/**").hasAnyRole("MEMBER", "ADMIN")
                .requestMatchers(request ->
                    request.getRequestURI().startsWith("/notices") && "GET".equals(request.getMethod())
                ).permitAll()
                .requestMatchers("/notices/**").hasRole("ADMIN")
                .requestMatchers(request ->
                    request.getRequestURI().startsWith("/items") && "GET".equals(request.getMethod())
                ).permitAll()
                .requestMatchers("/items/**").hasRole("ADMIN")
                .requestMatchers("/coins/**").hasRole("MEMBER")
                .requestMatchers("/userItems/**").hasAnyRole("MEMBER", "ADMIN")
                .requestMatchers(request ->
                    request.getRequestURI().startsWith("/pds") && "GET".equals(request.getMethod())
                ).permitAll()
                .requestMatchers("/pds/**").hasRole("ADMIN")

                .anyRequest().authenticated() // 나머지는 인증 필요
            )
            .formLogin(formLogin -> formLogin.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .addFilterAt(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler(messageSource)));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(tokenProvider);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(MessageSource messageSource) {
        return new CustomAccessDeniedHandler(messageSource);
    }

}
