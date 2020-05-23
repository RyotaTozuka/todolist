package com.todolist.webApp.User;

import com.todolist.entity.UserInformation;
import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.UserInformationService;
import com.todolist.webAppCommon.ControllerProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * ユーザ情報変更に関する画面のControllerクラス
 * ※管理者権限で実行するものはAdminControllerにて実装
 * @link com.todolist.webApp.Admin.AdminController
 */
@Controller
public class CreateUserController {

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    @ModelAttribute
    UserInformationForm setUpForm() {
        return new UserInformationForm();
    }

    /**
     * 新規登録ボタン押下
     *
     * @param passwordFirst 登録内容：パスワード一回目
     * @param passwordSecond 登録内容：パスワード二回目
     * @param form 入力されたユーザ情報
     * @param result エラー格納変数
     * @param model モデル
     * @return 一般ユーザ：login画面、管理者：管理者メニュー
     */
    @RequestMapping(value="user/createUser/create", method=RequestMethod.POST)
    public String insertUser(
            @RequestParam() String passwordFirst,
            @RequestParam() String passwordSecond,
            @ModelAttribute("userInformationForm") @Valid UserInformationForm form,
            BindingResult result,
            Model model) {

        controllerProcedure.addMastAttribute(model);

        if (result.hasErrors()) {
            return "user/createUser";
        }

        //userName uniqueチェック
        if (!userInformationService.isUniqueUserName(form.getUserName())) {
            model.addAttribute("userNameNotUnique",true);
            return "user/createUser";
        }

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        if (!passwordFirst.matches(passwordSecond)) {
            model.addAttribute("passwordNotTheSame", true);
            return "user/createUser";
        }

        UserInformation userInformation = new UserInformation();
        userInformation.setUserName(form.getUserName());
        userInformation.setUserPassword(passwordFirst);

        //ユーザ権限が設定されていなければ、一般ユーザ権限を設定
        if (form.getUserRole() != null) {
            userInformation.setUserRole(form.getUserRole());
        } else {
            userInformation.setUserRole("ROLE_USER");
        }

        userInformationService.insertUserInformation(userInformation);

        String userRole = secureUserDetailsService.getUserInformation().getUserRole();

        //ユーザがログインしており、管理者である場合は管理者メニュー画面に遷移
        if (("ROLE_ADMIN").matches(userRole)) {
            return "redirect:/admin/main";
        }
        return "redirect:/login";
    }
}