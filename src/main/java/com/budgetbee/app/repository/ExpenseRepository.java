package com.budgetbee.app.repository;
import com.budgetbee.app.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.time.LocalDate; import java.util.*; 
public interface ExpenseRepository extends JpaRepository<Expense, Long> { 
  List<Expense> findByUserOrderByDateDesc(User user);
  List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
