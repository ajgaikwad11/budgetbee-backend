package com.budgetbee.app.repository;
import com.budgetbee.app.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; 
public interface GoalRepository extends JpaRepository<Goal, Long> { List<Goal> findByUser(User user); }
