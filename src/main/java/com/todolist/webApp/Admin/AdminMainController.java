package com.todolist.webApp.Admin;

import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.UserInformationService;
import com.todolist.webAppCommon.ControllerProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * 管理者メイン画面のControllerクラス
 */
@Controller
public class AdminMainController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    /**
     * ユーザ情報一覧画面へ遷移する
     *
     * @param model モデル
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping("admin/main/userList")
    public String userList(Model model) {
        controllerProcedure.addMastAttribute(model);
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        List<UserInformationForm> userInformationForms = userInformationService.selectUserAll();

        model.addAttribute("userLists", userInformationForms);
        model.addAttribute("userId", userId);

        return "admin/userList";
    }

    /**
     * ユーザ新規作成画面に遷移
     *
     * @param model モデル
     * @return ユーザ新規作成画面のアドレス
     */
    @RequestMapping("admin/main/createUser")
    public String createUser(Model model) {

        controllerProcedure.addMastAttribute(model);
        UserInformationForm userInformationForm = new UserInformationForm();

        model.addAttribute("userInformationForm", userInformationForm);

        return "user/createUser";
    }
}