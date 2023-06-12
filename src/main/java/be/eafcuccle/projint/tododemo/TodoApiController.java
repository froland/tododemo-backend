package be.eafcuccle.projint.tododemo;

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
    TodoUser owner = getOwnerFromAuthentication(authentication);
    return ResponseEntity.ok(todoRepository.findByOwner(owner));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable UUID id, Authentication authentication) {
    TodoUser owner = getOwnerFromAuthentication(authentication);
    Todo foundTodo = todoRepository.findByIdAndOwner(id, owner).orElseThrow();
    return ResponseEntity.ok(foundTodo);
  }

  @PostMapping
  public ResponseEntity<Todo> createNewTodo(@RequestBody Todo todo, Authentication authentication, UriComponentsBuilder uriBuilder) {
    TodoUser owner = getOwnerFromAuthentication(authentication);
    Todo savedTodo = todoRepository.save(new Todo(owner, todo.getDescription()));
    URI todoUri = uriBuilder.pathSegment("api", "todos", "{id}").build(savedTodo.getId());
    return ResponseEntity.created(todoUri).body(savedTodo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodoById(@PathVariable UUID id, @RequestBody Todo todo, Authentication authentication) {
    TodoUser owner = getOwnerFromAuthentication(authentication);
    if (!id.equals(todo.getId())) {
      return ResponseEntity.badRequest().build();
    }
    Todo foundTodo = todoRepository.findByIdAndOwner(id, owner).orElseThrow();
    foundTodo.setDescription(todo.getDescription());
    foundTodo.setDone(todo.getDone());
    todoRepository.save(foundTodo);
    return ResponseEntity.ok(foundTodo);
  }

  private TodoUser getOwnerFromAuthentication(Authentication authentication) {
    String username = authentication.getName();
    TodoUser owner = todoUserRepository.findByUsername(username).orElseGet(() -> {
      TodoUser newUser = new TodoUser(username);
      return todoUserRepository.save(newUser);
    });
    return owner;
  }
}
