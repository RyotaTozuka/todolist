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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        addUserName(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/processing";
    }

    @RequestMapping("/main/complete")
    String mainComplete(Model model) {
        addUserName(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        List<TodoListForm> todoListForms = todoListService.getCompleteListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/complete";
    }

    @RequestMapping("/main/editing")
    String mainEditing(@RequestParam() Integer editFlag, @RequestParam(defaultValue = "0") Integer listId, Model model) {
        addUserName(model);

        TodoListForm todoListForm = new TodoListForm();

        if (editFlag.equals(NEW_CREATION)) {
            model.addAttribute("editFlag", NEW_CREATION);
        } else if (editFlag.equals(EDIT)) {
            todoListForm = todoListService.getTodoListByListId(listId);
            model.addAttribute("editFlag", EDIT);
        }
        model.addAttribute("todoList", todoListForm);

        return "/main/editing";
    }

    @RequestMapping(value = "/main/editTodoList", params = "insert")
    String insertTodoList(@RequestParam() String contents, @RequestParam() String limit, Model model) {
        addUserName(model);
        TodoList todoList = new TodoList();

        todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
        todoList.setListContents(contents);
        todoList.setListLimit(limit);

        todoListService.insertTodoList(todoList);

        return "redirect:/main/processing";
    }

    @RequestMapping(value = "/main/editTodoList", params = "completeId")
    String completeTodoList(@RequestParam() Integer completeId) {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        todoListService.updateToCompleteByListId(completeId, userId);

        return "redirect:/main/processing";
    }

    @RequestMapping(value = "/main/editTodoList", params = "processingId")
    String processingTodoList(@RequestParam() Integer processingId) {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        todoListService.updateToProcessingByListId(processingId, userId);

        return "redirect:/main/complete";
    }

    @RequestMapping(value = "/main/editTodoList", params = "editId")
    String editTodoList(@RequestParam() Integer editId, RedirectAttributes redirectAttribute) {
        redirectAttribute.addAttribute("editFlag", EDIT);
        redirectAttribute.addAttribute("listId", editId);

        return "redirect:/main/editing";
    }

    @RequestMapping(value = "/main/editTodoList", params = "updateId")
    String editTodoList(
            @RequestParam() Integer updateId,
            @RequestParam() String contents,
            @RequestParam() String limit,
            Model model) {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        TodoList todoList = new TodoList();

        todoList.setListId(updateId);
        todoList.setUserId(userId);
        todoList.setListContents(contents);
        todoList.setListLimit(limit);

        todoListService.updateTodoList(todoList);

        return "redirect:/main/processing";
    }

    @RequestMapping(value = "/main/editTodoList", params = "deleteId")
    String deleteTodoList(@RequestParam() Integer deleteId, @RequestParam(defaultValue = "NO_STATE") String status) {
        todoListService.deleteTodoList(deleteId);

        if (status.matches("complete")) {
            return "redirect:/main/complete";
        } else {
            return "redirect:/main/processing";
        }
    }

    @RequestMapping(value = "/main/editTodoList", params = "deleteCompleteAll")
    String deleteAllCompleteList() {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        todoListService.deleteAllCompleteListByUserId(userId);

        return "redirect:/main/processing";
    }

    private Model addUserName(Model model) {
        String userName = secureUserDetailsService.getUserInformation().getUserName();
        model.addAttribute("userName", userName);
        return model;
    }
}