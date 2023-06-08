package be.eafcuccle.projint.tododemo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
  @GetMapping("/api/hello")
  public ResponseEntity<String> helloWorld() {
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Hello World!");
  }
}
