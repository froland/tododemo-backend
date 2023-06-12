package be.eafcuccle.projint.tododemo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "todo")
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "done", nullable = false)
  private Boolean done;


  @Column(name = "description", nullable = false)
  private String description;

  @ManyToOne(optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private TodoUser owner;

  protected Todo() {
  }

  Todo(String description, TodoUser owner) {
    this.description = description;
    this.owner = owner;
    this.done = false;
  }

  public UUID getId() {
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

  public TodoUser getOwner() {
    return owner;
  }
}
