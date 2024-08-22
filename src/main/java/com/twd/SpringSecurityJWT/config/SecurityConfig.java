package com.twd.SpringSecurityJWT.config;

import com.twd.SpringSecurityJWT.service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private JWTAuthFIlter jwtAuthFIlter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request.requestMatchers(
                                "/auth/**",
                                "/public/**",
                                "/swagger-ui/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority("USER")
                        .requestMatchers("/admin_user/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()) //
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class
                );//jwtAuthFilter sera exécuté pour toutes les requêtes avant toute vérification des rôles ou des autorités.
                  //Ajoute le jwtAuthFilter avant le filtre UsernamePasswordAuthenticationFilter. Ce filtre (jwtAuthFilter) est
                // appliqué à toutes les requêtes pour vérifier la validité du jeton JWT avant d'essayer de traiter l'authentification.



        return httpSecurity.build();
    }

    @Bean //Le bean retourné est enregistré dans le contexte d'application et peut être injecté dans d'autres composants
    public AuthenticationProvider authenticationProvider() { //authenticationProvider :une implémentation de l'interface AuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();// utilisée pour l'authentification basée sur les données d'un UserDetailsService et un PasswordEncoder
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
