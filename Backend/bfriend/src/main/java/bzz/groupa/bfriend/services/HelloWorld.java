package bzz.groupa.bfriend.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {
    @GetMapping(path = "/hello", produces = "text/plain")
    public String helloWorld() {
        return "Hello World!";
    }
}
