package com.budgetbee.app.controller;
import com.budgetbee.app.model.*; import com.budgetbee.app.repository.IncomeRepository; import com.budgetbee.app.service.UserService;
import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/incomes") public class IncomeController extends BaseController {
  private final IncomeRepository repo; public IncomeController(UserService u, IncomeRepository r){ super(u); this.repo=r; }
  @GetMapping public List<Income> list(Authentication a){ return repo.findByUserOrderByDateDesc(authedUser(a)); }
  @PostMapping public Income create(@RequestBody Income e, Authentication a){ e.setId(null); e.setUser(authedUser(a)); return repo.save(e); }
  @PutMapping("/{id}") public ResponseEntity<Income> update(@PathVariable Long id, @RequestBody Income e, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ x.setAmount(e.getAmount()); x.setSource(e.getSource()); x.setDescription(e.getDescription()); x.setDate(e.getDate()); return ResponseEntity.ok(repo.save(x)); }).orElse(ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ repo.delete(x); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
  }
}
