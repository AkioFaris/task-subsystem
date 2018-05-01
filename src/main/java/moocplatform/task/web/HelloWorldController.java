package moocplatform.task.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: remove this class and web package
 * This is temporary class
 */
@RestController
public class HelloWorldController {
     private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/hi")
    public String sayHi(@RequestParam(value = "name") String name) {
        String msg = "Hi, " + name + "!";
        logger.info("Hello World");
        return msg;
    }
}
