package com.budgetbee.app.controller;
import com.budgetbee.app.model.User; import com.budgetbee.app.service.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/dashboard") public class DashboardController extends BaseController {
  private final DashboardService dash; public DashboardController(UserService u, DashboardService d){ super(u); this.dash=d; }
  @GetMapping("/summary") public Map<String,Object> summary(Authentication a){ return dash.summary(authedUser(a)); }
}
