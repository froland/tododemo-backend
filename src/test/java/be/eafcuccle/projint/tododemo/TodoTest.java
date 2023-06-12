package be.eafcuccle.projint.tododemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class TodoTest {
  @Autowired
  TestEntityManager entityManager;

  @Test
  void persistTodo() {
    TodoUser owner = new TodoUser("Test user");
    entityManager.persist(owner);
    Todo todo = new Todo(owner, "Test");
    Todo savedTodo = entityManager.persistFlushFind(todo);
    assertNotNull(savedTodo.getId());
    assertEquals("Test", savedTodo.getDescription());
  }
}
