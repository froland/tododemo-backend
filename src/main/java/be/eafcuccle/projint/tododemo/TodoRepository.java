package be.eafcuccle.projint.tododemo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
  List<Todo> findByOwner(TodoUser owner);

  Optional<Todo> findByIdAndOwner(UUID id, TodoUser owner);
}
