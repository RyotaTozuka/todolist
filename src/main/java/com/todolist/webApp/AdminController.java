package com.todolist.webApp;

import com.todolist.form.UserInformationForm;
import com.todolist.service.TodoListService;
import com.todolist.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private TodoListService todoListService;

    @RequestMapping("/admin/main")
    public String mainAdmin(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/admin/main";
    }

    @RequestMapping("/admin/userList")
    public String userList(Model model) {
        controllerProcedure.addMastAttribute(model);

        List<UserInformationForm> userInformationForms = userInformationService.selectUserAll();
        model.addAttribute("userLists", userInformationForms);

        return "/admin/userList";
    }

    @RequestMapping(value="/admin/editUserList", params="changeRoleId")
    public String changeRole(@RequestParam() Integer changeRoleId) {
        userInformationService.flipUserRoleByUserId(changeRoleId);

        return "redirect:/admin/userList";
    }

    @RequestMapping(value="/admin/editUserList", params="deleteId")
    public String deleteUser(@RequestParam() Integer deleteId) {
        todoListService.deleteListByUserId(deleteId);
        userInformationService.deleteUserByUserId(deleteId);

        return "redirect:/admin/userList";
    }
}
