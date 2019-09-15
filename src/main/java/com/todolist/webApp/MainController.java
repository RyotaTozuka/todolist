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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private static final Integer NEW_CREATION = 0;
    private static final Integer EDIT = 1;

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
        List<TodoListForm> todoListForms= todoListService.getTodoListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/processing";
    }

    @RequestMapping("/main/complete")
    String mainComplete(Model model) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        List<TodoListForm> todoListForms= todoListService.getCompleteListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/complete";
    }

    @RequestMapping("/main/editing")
    String mainEditing(@RequestParam() Integer editId, Model model) {

        if (editId.equals(NEW_CREATION)) {
            model.addAttribute("editId",0);
        }
        return "/main/editing";
    }

    @RequestMapping(value="/main/editTodoList", params="insert")
    String insertTodoList(@RequestParam() String contents, @RequestParam() String limit, Model model) {
        TodoList todoList = new TodoList();

        todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
        todoList.setListContents(contents);
        todoList.setListLimit(limit);

        todoListService.insertTodoList(todoList);

        return "redirect:/main/processing";
    }

    @RequestMapping(value="/main/editTodoList", params="completeId")
    String completeTodoList(@RequestParam() Integer completeId,  Model model) {
        TodoList todoList = new TodoList();

        todoListService.updateToCompleteByListId(completeId);

        return "redirect:/main/processing";
    }
}