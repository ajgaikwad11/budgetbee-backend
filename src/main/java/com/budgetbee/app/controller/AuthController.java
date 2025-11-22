package com.budgetbee.app.controller;
import com.budgetbee.app.model.User; import com.budgetbee.app.security.JwtUtils; import com.budgetbee.app.service.UserService;
import jakarta.validation.constraints.*; import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.*; import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/auth") @Validated public class AuthController {
  private final UserService userService; private final AuthenticationManager authManager; private final JwtUtils jwt;
  public AuthController(UserService u, AuthenticationManager a, JwtUtils j){ this.userService=u; this.authManager=a; this.jwt=j; }
  public record SignupReq(@NotBlank String name, @Email String email, @NotBlank String password){} 
  public record LoginReq(@Email String email, @NotBlank String password){}
  @PostMapping("/signup") public ResponseEntity<?> signup(@RequestBody SignupReq req){
    User u = userService.register(req.name(), req.email(), req.password()); String token = jwt.generateToken(u.getEmail());
    return ResponseEntity.ok(Map.of("token", token, "user", Map.of("id", u.getId(), "name", u.getName(), "email", u.getEmail())));
  }
  @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginReq req){
    Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
    User u = userService.findByEmail(req.email()); String token = jwt.generateToken(req.email());
    return ResponseEntity.ok(Map.of("token", token, "user", Map.of("id", u.getId(), "name", u.getName(), "email", u.getEmail())));
  }
}
