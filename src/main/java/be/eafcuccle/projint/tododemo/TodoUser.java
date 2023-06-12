package be.eafcuccle.projint.tododemo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "todo_user")
public class TodoUser {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  protected TodoUser() {}

  TodoUser(String username) {
    this.username = username;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

}
