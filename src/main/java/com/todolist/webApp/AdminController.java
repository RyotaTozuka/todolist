package com.todolist.webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @RequestMapping("/admin/main")
    public String mainAdmin(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/admin/main";
    }

    @RequestMapping("/admin/userList")
    public String userList(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/admin/userList";
    }
}
