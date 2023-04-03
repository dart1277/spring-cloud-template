package com.cx.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$12$GgZF31WxBk1Y28mnkhGsNuTetSiedjbkQd4nPBeLZMyA9Qd8mHPZS")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(SecurityConfig::configure);
        return http.build();
    }

    private static void configure(final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
        try {
            authz.requestMatchers(antMatcher("/login")).permitAll()
                    .requestMatchers(antMatcher("/kafka/**")).permitAll()
                    .requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN")
                    .requestMatchers(antMatcher("/**")).hasAnyRole("ADMIN", "USER")
                    .and()
                    .formLogin()
                    .loginPage("/login.html")
//                    .loginProcessingUrl("/perform_login")
//                    .defaultSuccessUrl("/homepage.html", true)
//                    .failureUrl("/login.html?error=true")
//                    .failureHandler(authenticationFailureHandler())
//                    .and()
//                    .logout()
//                    .logoutUrl("/perform_logout")
//                    .deleteCookies("JSESSIONID")
//                    .logoutSuccessHandler(logoutSuccessHandler())
                    .and().logout().logoutSuccessUrl("/login").permitAll()
                    .and().csrf().disable();
        } catch (Exception ex) {
            throw new IllegalStateException();
        }
    }
}