package be.eafcuccle.projint.tododemo;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;
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

  protected TodoUser() {
  }

  TodoUser(String username) {
    this.username = username;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoUser todoUser = (TodoUser) o;
    return Objects.equals(id, todoUser.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TodoUser.class.getSimpleName() + "[", "]")
      .add("username='" + username + "'")
      .toString();
  }
}
