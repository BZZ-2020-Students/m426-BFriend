package bzz.groupa.bfriend.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping(path = "/", produces = "text/plain")
    public String helloWorld() {
        return "Hello World!";
    }
}
