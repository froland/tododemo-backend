package be.eafcuccle.projint.tododemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class TodoUserTest {
  @Autowired
  TestEntityManager entityManager;

  @Test
  void persistTodoUser() {
    TodoUser user = new TodoUser("user123@example.net");
    TodoUser savedUser = entityManager.persistFlushFind(user);
    assertNotNull(savedUser.getId());
    assertEquals("user123@example.net", savedUser.getEmail());
  }
}
