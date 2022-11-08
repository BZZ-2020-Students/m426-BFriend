package bzz.groupa.bfriend.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/public")
public class HelloWorld {
    @RequestMapping(path = "/info", produces = "text/plain")
    public String helloWorld() {
        return "Hello World!";
    }
}
