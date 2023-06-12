package be.eafcuccle.projint.tododemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoUserTest {
  @Autowired
  TestEntityManager entityManager;

  @Test
  void persistTodoUser() {
    TodoUser user = new TodoUser("Test user");
    TodoUser savedUser = entityManager.persistFlushFind(user);
    assertNotNull(savedUser.getId());
    assertEquals("Test user", savedUser.getUsername());
  }
}
