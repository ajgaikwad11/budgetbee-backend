package com.budgetbee.app.controller;
import com.budgetbee.app.model.*; import com.budgetbee.app.repository.GoalRepository; import com.budgetbee.app.service.UserService;
import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/goals") public class GoalController extends BaseController {
  private final GoalRepository repo; public GoalController(UserService u, GoalRepository r){ super(u); this.repo=r; }
  @GetMapping public List<Goal> list(Authentication a){ return repo.findByUser(authedUser(a)); }
  @PostMapping public Goal create(@RequestBody Goal g, Authentication a){ g.setId(null); g.setUser(authedUser(a)); return repo.save(g); }
  @PutMapping("/{id}") public ResponseEntity<Goal> update(@PathVariable Long id, @RequestBody Goal g, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ x.setTitle(g.getTitle()); x.setTargetAmount(g.getTargetAmount()); x.setCurrentAmount(g.getCurrentAmount()); x.setDeadline(g.getDeadline()); return ResponseEntity.ok(repo.save(x)); }).orElse(ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ repo.delete(x); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
  }
}
