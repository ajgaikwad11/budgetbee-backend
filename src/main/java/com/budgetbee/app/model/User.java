package com.budgetbee.app.model;
import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name;
  @Column(nullable=false, unique=true) private String email;
  @Column(nullable=false) private String passwordHash;
  private Instant createdAt = Instant.now();
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String p){this.passwordHash=p;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant i){this.createdAt=i;}
}
