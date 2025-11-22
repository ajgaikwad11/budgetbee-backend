package com.budgetbee.app.security;
import com.budgetbee.app.service.UserService; import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.io.IOException;
@Component public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils; private final UserService userService;
  public JwtAuthFilter(JwtUtils j, UserService u){ this.jwtUtils=j; this.userService=u; }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    String header=req.getHeader(HttpHeaders.AUTHORIZATION);
    if(header!=null && header.startsWith("Bearer ")){ String token=header.substring(7); String email=jwtUtils.validateAndGetSubject(token);
      if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
        UserDetails ud = userService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req)); SecurityContextHolder.getContext().setAuthentication(auth);
      } }
    chain.doFilter(req, res);
  }
}
