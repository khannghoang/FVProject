package com.freshvotes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
            User.withUsername("trevor@craftycodr.com")
                .password("{noop}asdfasdf") // "{noop}" để chỉ định không mã hóa mật khẩu, hoặc sử dụng mã hóa nếu cần.
                .roles("USER")
                .build()
        );
        return manager;
    }

    @SuppressWarnings("removal")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .anyRequest().hasRole("USER")
                )
                .formLogin((form) -> form
                                .loginPage("/login") // Trang đăng nhập tùy chỉnh
                                .permitAll()
                )
                .logout((logout) -> logout
                                .logoutUrl("/logout") // URL xử lý đăng xuất
                                .permitAll()
                );

        return http.build();
    }
}
