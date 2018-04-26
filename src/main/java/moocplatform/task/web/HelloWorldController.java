package moocplatform.task.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: remove this class and web package
 * This is temporary class
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hi")
    public String sayHi(@RequestParam(value = "name") String name) {
        return "Hi, " + name + "!";
    }
}
