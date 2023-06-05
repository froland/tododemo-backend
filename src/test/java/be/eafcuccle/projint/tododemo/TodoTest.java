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
    Todo todo = new Todo("Test");
    Todo savedTodo = entityManager.persistFlushFind(todo);
    assertNotNull(savedTodo.getId());
    assertEquals("Test", savedTodo.getDescription());
  }
}
