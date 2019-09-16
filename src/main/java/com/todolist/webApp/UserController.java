package com.todolist.webApp;

import com.todolist.entity.UserInformation;
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

    @RequestMapping(value="/user/insertUser", method=RequestMethod.POST)
    public String insertUser(
            @RequestParam() String userName,
            @RequestParam() String passwordFirst,
            @RequestParam() String passwordSecond,
            @RequestParam(defaultValue = "ROLE_USER") String newUsersRole) {

        if (!passwordFirst.matches(passwordSecond)) {
            return "user/createUser";
        }

        UserInformation userInformation = new UserInformation();
        userInformation.setUserName(userName);
        userInformation.setUserPassword(passwordFirst);

        if (("ROLE_ADMIN").matches(newUsersRole)) {
            userInformation.setUserRole("ROLE_ADMIN");
        } else {
            userInformation.setUserRole("ROLE_USER");
        }

        userInformationService.insertUserInformation(userInformation);

        String userRole = secureUserDetailsService.getUserInformation().getUserRole();

        if (userRole != null) {
             if (("ROLE_ADMIN").matches(userRole)) {
                 return "redirect:/admin/main";
             }
        }
        return "redirect:/login";
    }
}
