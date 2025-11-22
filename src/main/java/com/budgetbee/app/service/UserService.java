package com.budgetbee.app.service;
import com.budgetbee.app.model.User; import com.budgetbee.app.repository.UserRepository;
import org.springframework.security.core.userdetails.*; import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import java.util.List;
@Service public class UserService implements UserDetailsService {
  private final UserRepository repo; private final PasswordEncoder enc;
  public UserService(UserRepository r, PasswordEncoder e){ this.repo=r; this.enc=e; }
  public User register(String name, String email, String raw){ if(repo.existsByEmail(email)) throw new IllegalArgumentException("Email already in use");
    User u=new User(); u.setName(name); u.setEmail(email); u.setPasswordHash(enc.encode(raw)); return repo.save(u); }
  public User findByEmail(String email){ return repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Not found")); }
  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u=findByEmail(username); return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER"))); }
}
