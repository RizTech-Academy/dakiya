package com.riztech.dakiya;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    InMemoryUserDetailsManager users(PasswordEncoder encoder) {
        UserDetails staff = User.withUsername("staff")
            .password(encoder.encode("staff-pass-123")).roles("STAFF").build();
        UserDetails admin = User.withUsername("admin")
            .password(encoder.encode("admin-pass-123")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(staff, admin);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .authorizeHttpRequests(a -> a
                .requestMatchers("/ping").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .httpBasic(org.springframework.security.config.Customizer.withDefaults());
        return http.build();
    }
}
