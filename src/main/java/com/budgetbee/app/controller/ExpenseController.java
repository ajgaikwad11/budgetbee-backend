package com.budgetbee.app.controller;
import com.budgetbee.app.model.*; import com.budgetbee.app.repository.ExpenseRepository; import com.budgetbee.app.service.UserService;
import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/expenses") public class ExpenseController extends BaseController {
  private final ExpenseRepository repo; public ExpenseController(UserService u, ExpenseRepository r){ super(u); this.repo=r; }
  @GetMapping public List<Expense> list(Authentication a){ return repo.findByUserOrderByDateDesc(authedUser(a)); }
  @PostMapping public Expense create(@RequestBody Expense e, Authentication a){ e.setId(null); e.setUser(authedUser(a)); return repo.save(e); }
  @PutMapping("/{id}") public ResponseEntity<Expense> update(@PathVariable Long id, @RequestBody Expense e, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ x.setAmount(e.getAmount()); x.setCategory(e.getCategory()); x.setDescription(e.getDescription()); x.setDate(e.getDate()); return ResponseEntity.ok(repo.save(x)); }).orElse(ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id, Authentication a){
    var u = authedUser(a); return repo.findById(id).filter(x->x.getUser().getId().equals(u.getId())).map(x->{ repo.delete(x); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
  }
}
