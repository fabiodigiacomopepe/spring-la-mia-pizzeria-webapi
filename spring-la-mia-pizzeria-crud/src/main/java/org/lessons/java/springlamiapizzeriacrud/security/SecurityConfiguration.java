package org.lessons.java.springlamiapizzeriacrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    // Con questo metodo gestisco l'autorizzazione di accesso alle varie rotte
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/pizzas", "/pizzas/show/**").hasAnyAuthority("ADMIN", "USER")              // Index/Show   PIZZA
                .requestMatchers("/ingredients", "/offers", "/users").hasAuthority("ADMIN")                             // Index        INGREDIENTI/OFFERTE/UTENTI
                .requestMatchers("/pizzas/create", "/offers/create/**", "/ingredients/**").hasAuthority("ADMIN")        // Create       PIZZA/OFFERTE/INGREDIENTI
                .requestMatchers("/pizzas/edit/**", "/offers/edit/**").hasAuthority("ADMIN")                            // Edit         PIZZA/OFFERTE
                .requestMatchers("/pizzas/delete/**", "/ingredients/delete/**").hasAuthority("ADMIN")                   // Delete       PIZZA/INGREDIENTI
                .requestMatchers(HttpMethod.POST, "/pizzas/**", "/ingredients/**", "/offers/**").hasAuthority("ADMIN")  // Metodi POST  PIZZA/INGREDIENTI/OFFERTE
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        return http.build();
    }

    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
