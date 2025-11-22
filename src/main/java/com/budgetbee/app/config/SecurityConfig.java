package com.budgetbee.app.config;
import com.budgetbee.app.security.JwtAuthFilter; import org.springframework.context.annotation.*; import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*; import java.util.*; 
@Configuration public class SecurityConfig {
  private final UserDetailsService uds; private final JwtAuthFilter jwt;
  public SecurityConfig(UserDetailsService uds, JwtAuthFilter jwt){ this.uds=uds; this.jwt=jwt; }
  @Bean public SecurityFilterChain chain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable()).cors(cors->{
      UrlBasedCorsConfigurationSource src=new UrlBasedCorsConfigurationSource();
      CorsConfiguration conf=new CorsConfiguration(); conf.setAllowedOrigins(List.of("*"));
      conf.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")); conf.setAllowedHeaders(List.of("*"));
      src.registerCorsConfiguration("/**", conf); cors.configurationSource(src);
    }).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/").permitAll().anyRequest().authenticated())
      .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  @Bean public AuthenticationProvider authenticationProvider(){ DaoAuthenticationProvider p=new DaoAuthenticationProvider(); p.setUserDetailsService(uds); p.setPasswordEncoder(passwordEncoder()); return p; }
  @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception { return c.getAuthenticationManager(); }
}
