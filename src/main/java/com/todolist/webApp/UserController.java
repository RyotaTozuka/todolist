package com.todolist.webApp;

import com.todolist.entity.UserInformation;
import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.UserInformationService;
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

    @ModelAttribute
    UserInformationForm setUpForm() {
        return new UserInformationForm();
    }

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
        if (!secureUserDetailsService.checkPasswordIsValidated(oldPassword)) {
            model.addAttribute("oldPasswordMissMatch", true);
            return "/user/changePassword";
        }

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        if (!newPasswordFirst.equals(newPasswordSecond)) {
            model.addAttribute("newPasswordMissMatch", true);
            return "/user/changePassword";
        }

        userInformationService.updateUserPassword(newPasswordFirst);

        return "redirect:/main/processing";
    }

    /**
     * ユーザ新規作成画面に遷移
     *
     * @param model モデル
     * @param userNameNotUnique ユーザ作成時、登録ユーザ名が既に存在する場合、trueでリダイレクトされる
     * @param passwordNotTheSame ユーザ作成時、パスワードが1回目と2回目で一致しない場合、trueでリダイレクトされる
     * @return ユーザ新規作成画面のアドレス
     */
    @RequestMapping("/user/createUser")
    public String createUser(@RequestParam(defaultValue = "false") boolean userNameNotUnique,
                             @RequestParam(defaultValue = "false") boolean passwordNotTheSame,
                             Model model) {

        controllerProcedure.addMastAttribute(model);
        UserInformationForm userInformationForm = new UserInformationForm();

        model.addAttribute("userNameNotUnique", userNameNotUnique);
        model.addAttribute("passwordNotTheSame", passwordNotTheSame);
        model.addAttribute("userInformationForm", userInformationForm);

        return "/user/createUser";
    }

    /**
     * 入力されたユーザ情報に基づき、ユーザ情報を新規登録し、
     * ログイン画面に遷移、管理者の場合は管理者メニュー画面に遷移
     *
     * @param passwordFirst 登録内容：パスワード一回目
     * @param passwordSecond 登録内容：パスワード二回目
     * @param form 入力されたユーザ情報
     * @param result エラー格納変数
     * @param model モデル
     * @return 一般ユーザ：login画面、管理者：管理者メニュー
     */
    @RequestMapping(value="/user/insertUser", method=RequestMethod.POST)
    public String insertUser(
            @RequestParam() String passwordFirst,
            @RequestParam() String passwordSecond,
            @ModelAttribute("userInformationForm") @Valid UserInformationForm form,
            BindingResult result,
            Model model) {

        controllerProcedure.addMastAttribute(model);

        if (result.hasErrors()) {
            return "/user/createUser";
        }

        //userName uniqueチェック
        if (!userInformationService.isUniqueUserName(form.getUserName())) {
            model.addAttribute("userNameNotUnique",true);
            return "/user/createUser";
        }

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        if (!passwordFirst.matches(passwordSecond)) {
            model.addAttribute("passwordNotTheSame", true);
            return "/user/createUser";
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
