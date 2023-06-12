package be.eafcuccle.projint.tododemo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoUserRepository extends JpaRepository<TodoUser, UUID> {
  Optional<TodoUser> findByUsername(String username);

}
