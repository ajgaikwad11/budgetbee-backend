package com.budgetbee.app.model;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.time.LocalDate;
@Entity @Table(name="goals")
public class Goal {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
  private String title; private BigDecimal targetAmount; private BigDecimal currentAmount = BigDecimal.ZERO;
  private LocalDate deadline; private Instant createdAt = Instant.now();
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public User getUser(){return user;} public void setUser(User u){this.user=u;}
  public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
  public BigDecimal getTargetAmount(){return targetAmount;} public void setTargetAmount(BigDecimal t){this.targetAmount=t;}
  public BigDecimal getCurrentAmount(){return currentAmount;} public void setCurrentAmount(BigDecimal c){this.currentAmount=c;}
  public LocalDate getDeadline(){return deadline;} public void setDeadline(LocalDate d){this.deadline=d;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant i){this.createdAt=i;}
}
