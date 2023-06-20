package be.eafcuccle.projint.tododemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoApiController {
  private final TodoRepository todoRepository;
  private final TodoUserRepository todoUserRepository;

  public TodoApiController(TodoRepository todoRepository, TodoUserRepository todoUserRepository) {
    this.todoRepository = todoRepository;
    this.todoUserRepository = todoUserRepository;
  }

  @GetMapping
  public ResponseEntity<List<Todo>> getAllTodos(Authentication authentication) {
    if (hasAuthority(authentication, "SCOPE_read:all-todos")) {
      return ResponseEntity.ok(todoRepository.findAll());
    } else if (hasAuthority(authentication, "SCOPE_read:todos")) {
      TodoUser owner = getOwnerFromAuthentication(authentication);
      return ResponseEntity.ok(todoRepository.findByOwner(owner));
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable UUID id, Authentication authentication) {
    if (hasAuthority(authentication, "SCOPE_read:all-todos") || hasAuthority(authentication, "SCOPE_read:todos")) {
      TodoUser owner = getOwnerFromAuthentication(authentication);
      return todoRepository.findByIdAndOwner(id, owner).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @PostMapping
  public ResponseEntity<Todo> createNewTodo(@RequestBody Todo todo, UriComponentsBuilder uriBuilder, Authentication authentication) {
    if (hasAuthority(authentication, "SCOPE_write:todos")) {
      TodoUser owner = getOwnerFromAuthentication(authentication);
      Todo savedTodo = todoRepository.save(new Todo(todo.getDescription(), owner));
      URI todoUri = uriBuilder.pathSegment("api", "todos", "{id}").build(savedTodo.getId());
      return ResponseEntity.created(todoUri).body(savedTodo);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodoById(@PathVariable UUID id, @RequestBody Todo todo, Authentication authentication) {
    if (hasAuthority(authentication, "SCOPE_write:todos")) {
      if (!id.equals(todo.getId())) {
        return ResponseEntity.badRequest().build();
      }
      TodoUser owner = getOwnerFromAuthentication(authentication);
      Todo foundTodo = todoRepository.findByIdAndOwner(id, owner).orElseThrow();
      foundTodo.setDescription(todo.getDescription());
      foundTodo.setDone(todo.getDone());
      todoRepository.save(foundTodo);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  private TodoUser getOwnerFromAuthentication(Authentication authentication) {
    String username = authentication.getName();
    return todoUserRepository.findByUsername(username).orElseGet(() -> {
      TodoUser newUser = new TodoUser(username);
      return todoUserRepository.save(newUser);
    });
  }

  private static boolean hasAuthority(Authentication authentication, String expectedAuthority) {
    return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(expectedAuthority));
  }

}
