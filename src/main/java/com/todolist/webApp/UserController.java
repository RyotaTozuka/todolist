package com.todolist.webApp;

import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    @RequestMapping("/user/changePassword")
    public String changePassword(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/user/changePassword";
    }

    @RequestMapping(value="/user/updatePassword", method= RequestMethod.POST)
    public String updatePassword(
            @RequestParam() String oldPassword,
            @RequestParam() String newPasswordFirst,
            @RequestParam() String newPasswordSecond,
            Model model) {
        controllerProcedure.addMastAttribute(model);

        if (!secureUserDetailsService.checkPasswordValidation(oldPassword)) {
            return "/user/changePassword";
        }

        if (!newPasswordFirst.equals(newPasswordSecond)) {
            return "/user/changePassword";
        }

        userInformationService.updateUserPassword(newPasswordFirst);

        return "redirect:/main/processing";
    }

    @RequestMapping("user/createUser")
    public String createUser(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/user/createUser";
    }
}
