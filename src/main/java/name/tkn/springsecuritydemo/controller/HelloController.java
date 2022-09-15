package name.tkn.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: lz
 * @time: 2022/9/15 13:09
 */
@Controller
public class HelloController {

    @GetMapping("index")
    public String index(){
        return "login";
    }
    @GetMapping("findAll")
    @ResponseBody
    public String findAll(){
        return "findAll";
    }
    @PostMapping("/success")
    @ResponseBody
    public String success(){
        return "success";
    }
    @PostMapping("/fail")
    @ResponseBody
    public String fail() {
        return "fail";
    }

    @GetMapping("/find")
    @ResponseBody
    public String find(){
        return "find";
    }

}
