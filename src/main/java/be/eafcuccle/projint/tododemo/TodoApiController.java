package be.eafcuccle.projint.tododemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoApiController {
  private final TodoRepository todoRepository;

  public TodoApiController(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @GetMapping
  public ResponseEntity<List<Todo>> getAllTodos() {
    return ResponseEntity.ok(todoRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable UUID id) {
    Todo foundTodo = todoRepository.findById(id).orElseThrow();
    return ResponseEntity.ok(foundTodo);
  }

  @PostMapping
  public ResponseEntity<Todo> createNewTodo(@RequestBody Todo todo, UriComponentsBuilder uriBuilder) {
    Todo savedTodo = todoRepository.save(new Todo(todo.getDescription()));
    URI todoUri = uriBuilder.pathSegment("api", "todos", "{id}").build(savedTodo.getId());
    return ResponseEntity.created(todoUri).body(savedTodo);
  }
}
