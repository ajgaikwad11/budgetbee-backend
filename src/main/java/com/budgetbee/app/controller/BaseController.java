package com.budgetbee.app.controller;
import com.budgetbee.app.model.User; import com.budgetbee.app.service.UserService; import org.springframework.security.core.Authentication;
public abstract class BaseController { private final UserService userService; protected BaseController(UserService u){ this.userService=u; }
  protected User authedUser(Authentication a){ return userService.findByEmail(a.getName()); } }
