package com.budgetbee.app.repository;
import com.budgetbee.app.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.time.LocalDate; import java.util.*; 
public interface IncomeRepository extends JpaRepository<Income, Long> { 
  List<Income> findByUserOrderByDateDesc(User user);
  List<Income> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
