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

  @GetMapping("/{todoId}")
  public ResponseEntity<Todo> getTodoById(@PathVariable UUID todoId, Authentication authentication) {
    TodoUser owner = getOwnerFromAuthentication(authentication);
    Todo foundTodo = todoRepository.findByIdAndOwner(todoId, owner).orElseThrow();
    return ResponseEntity.ok(foundTodo);
  }

  @PostMapping
  public ResponseEntity<Todo> createNewTodo(@RequestBody Todo todo, Authentication authentication, UriComponentsBuilder uriBuilder) {
    TodoUser owner = getOwnerFromAuthentication(authentication);
    Todo savedTodo = todoRepository.save(new Todo(owner, todo.getDescription()));
    URI todoUri = uriBuilder.pathSegment("api", "todos", "{id}").build(savedTodo.getId());
    return ResponseEntity.created(todoUri).body(savedTodo);
  }

  private TodoUser getOwnerFromAuthentication(Authentication authentication) {
    TodoUser owner = todoUserRepository.findByUsername(authentication.getName()).orElseGet(() -> {
      TodoUser newUser = new TodoUser(authentication.getName());
      return todoUserRepository.save(newUser);
    });
    return owner;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodoById(@PathVariable UUID id, @RequestBody Todo todo) {
    if (!id.equals(todo.getId())) {
      return ResponseEntity.badRequest().build();
    }
    Todo foundTodo = todoRepository.findById(id).orElseThrow();
    foundTodo.setDescription(todo.getDescription());
    foundTodo.setDone(todo.getDone());
    todoRepository.save(foundTodo);
    return ResponseEntity.ok(foundTodo);
  }
}
