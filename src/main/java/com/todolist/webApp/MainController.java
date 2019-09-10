package com.todolist.webApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/main/processing")
    String mainProcessing() {return "/main/processing";}
}
