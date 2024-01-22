package kory.spring.com.bekoryfurniture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controoler {

    @GetMapping("hello")
    public String hello(){
        return "Hello kory";
    }
}
