package com.budgetbee.app.model;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.LocalDate;
@Entity @Table(name="incomes")
public class Income {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
  private BigDecimal amount; private String source; private String description; private LocalDate date;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public User getUser(){return user;} public void setUser(User u){this.user=u;}
  public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal a){this.amount=a;}
  public String getSource(){return source;} public void setSource(String s){this.source=s;}
  public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
  public LocalDate getDate(){return date;} public void setDate(LocalDate d){this.date=d;}
}
