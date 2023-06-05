package be.eafcuccle.projint.tododemo;

import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "done", nullable = false)
  private Boolean done;

  @Column(name = "description", nullable = false)
  private String description;

  protected Todo() {
  }

  Todo(String description) {
    this.description = description;
    this.done = false;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getDone() {
    return done;
  }

  public void setDone(Boolean done) {
    this.done = done;
  }

}
