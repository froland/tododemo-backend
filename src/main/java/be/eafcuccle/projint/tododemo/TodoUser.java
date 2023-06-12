package be.eafcuccle.projint.tododemo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class TodoUser {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  TodoUser(String username) {
    this.username = username;
  }

  protected TodoUser() {
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return username;
  }
}
