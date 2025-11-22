package com.budgetbee.app.service;
import com.budgetbee.app.model.*; import com.budgetbee.app.repository.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.time.*; import java.util.*;
@Service public class DashboardService {
  private final ExpenseRepository exp; private final IncomeRepository inc;
  public DashboardService(ExpenseRepository e, IncomeRepository i){ this.exp=e; this.inc=i; }
  public Map<String,Object> summary(User user){
    List<Expense> expenses = exp.findByUserOrderByDateDesc(user);
    List<Income> incomes  = inc.findByUserOrderByDateDesc(user);
    BigDecimal totalExpense = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalIncome  = incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal balance = totalIncome.subtract(totalExpense);
    YearMonth now = YearMonth.now(); LocalDate s = now.atDay(1); LocalDate eom = now.atEndOfMonth();
    BigDecimal monthExpense = exp.findByUserAndDateBetween(user, s, eom).stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal monthIncome  = inc.findByUserAndDateBetween(user, s, eom).stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    Map<String,Object> m=new HashMap<>(); m.put("totalExpense", totalExpense); m.put("totalIncome", totalIncome); m.put("balance", balance);
    m.put("monthExpense", monthExpense); m.put("monthIncome", monthIncome); return m;
  }
}
