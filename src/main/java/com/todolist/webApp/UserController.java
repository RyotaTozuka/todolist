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

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * ユーザ情報変更に関する画面のControllerクラス
 * ※管理者権限で実行するものはAdminControllerにて実装
 * @link com.todolist.webApp.AdminController
 */
@Controller
public class UserController {

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    /**
     * パスワード変更画面に遷移
     *
     * @param model モデル
     * @return パスワード変更画面のアドレス
     */
    @RequestMapping("/user/changePassword")
    public String changePassword(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/user/changePassword";
    }

    /**
     * パスワードを変更し、未完了リスト画面に遷移
     *
     * @param oldPassword ユーザ認証のために入力された旧パスワード
     * @param newPasswordFirst 新パスワード一回目
     * @param newPasswordSecond 新パスワード二回目
     * @param model モデル
     * @return 未完了リスト画面のアドレス　※精査エラーの場合はパスワード変更画面のアドレス
     */
    @RequestMapping(value="/user/updatePassword", method= RequestMethod.POST)
    public String updatePassword(
            @RequestParam() String oldPassword,
            @RequestParam() String newPasswordFirst,
            @RequestParam() String newPasswordSecond,
            Model model) {
        controllerProcedure.addMastAttribute(model);

        //旧パスワードが違う場合はパスワード変更画面に戻る
        //todo:精査エラーの実装
        if (!secureUserDetailsService.checkPasswordValidation(oldPassword)) {
            return "/user/changePassword";
        }

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        //todo:精査エラーの実装
        if (!newPasswordFirst.equals(newPasswordSecond)) {
            return "/user/changePassword";
        }

        userInformationService.updateUserPassword(newPasswordFirst);

        return "redirect:/main/processing";
    }

    /**
     * ユーザ新規作成画面に遷移
     *
     * @param model モデル
     * @return ユーザ新規作成画面のアドレス
     */
    @RequestMapping("user/createUser")
    public String createUser(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/user/createUser";
    }

    /**
     * 入力されたユーザ情報に基づき、ユーザ情報を新規登録し、
     * ログイン画面に遷移、管理者の場合は管理者メニュー画面に遷移
     *
     * @param userName 登録内容：ユーザ名
     * @param passwordFirst 登録内容：パスワード一回目
     * @param passwordSecond 登録内容：パスワード二回目
     * @param newUsersRole 登録内容：管理者権限（デフォルトは一般権限）
     * @return 一般ユーザ：login画面、管理者：管理者メニュー
     */
    @RequestMapping(value="/user/insertUser", method=RequestMethod.POST)
    public String insertUser(
            @RequestParam() String userName,
            @RequestParam() String passwordFirst,
            @RequestParam() String passwordSecond,
            @RequestParam(defaultValue = "ROLE_USER") String newUsersRole) {

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        //todo:精査エラーの実装
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

        //ユーザがログインしており、管理者である場合は管理者メニュー画面に遷移
        if (("ROLE_ADMIN").matches(userRole)) {
            return "redirect:/admin/main";
        }
        return "redirect:/login";
    }
}
