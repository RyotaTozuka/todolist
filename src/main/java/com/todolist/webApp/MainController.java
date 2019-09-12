package com.todolist.webApp;

import com.todolist.entity.TodoList;
import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private TodoListService todoListService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @ModelAttribute
    TodoListForm setUpForm() {
        return new TodoListForm();
    }

    @RequestMapping("/main/processing")
    String mainProcessing(Model model) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        List<TodoListForm> todoListForms= todoListService.getListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        model.addAttribute("test","test");
        return "/main/processing";
    }
}
