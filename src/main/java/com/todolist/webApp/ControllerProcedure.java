package com.todolist.webApp;

import com.todolist.entity.UserInformation;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ControllerProcedure {
    private static final boolean ROLE_ADMIN = true;
    private static final boolean ROLE_USER = false;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    public void addMastAttribute(Model model) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        model.addAttribute("userName", userInformation.getUserName());
        if (("ROLE_ADMIN").matches(userInformation.getUserRole())) {
            model.addAttribute("userRole", ROLE_ADMIN);
        } else {
            model.addAttribute("userRole", ROLE_USER);
        }
    }
}
