package com.todolist.webApp.User;

import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.UserInformationService;
import com.todolist.Util.ControllerUtil.AddParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * ユーザ情報変更に関する画面のControllerクラス
 * ※管理者権限で実行するものはAdminControllerにて実装
 * @link com.todolist.webApp.Admin.AdminController
 */
@Controller
public class ChangePasswordController {

    @Autowired
    private AddParamUtil addParamUtil;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private UserInformationService userInformationService;

    @ModelAttribute
    UserInformationForm setUpForm() {
        return new UserInformationForm();
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
    @RequestMapping(value="user/changePassword/change", method= RequestMethod.POST)
    public String updatePassword(
            @RequestParam() String oldPassword,
            @RequestParam() String newPasswordFirst,
            @RequestParam() String newPasswordSecond,
            Model model) {
        addParamUtil.addMastAttribute(model);

        //旧パスワードが違う場合はパスワード変更画面に戻る
        if (!secureUserDetailsService.checkPasswordIsValidated(oldPassword)) {
            model.addAttribute("oldPasswordMissMatch", true);
            return "user/changePassword";
        }

        //新パスワードの一回目と二回目の入力値が異なる場合はパスワード変更画面に戻る
        if (!newPasswordFirst.equals(newPasswordSecond)) {
            model.addAttribute("newPasswordMissMatch", true);
            return "user/changePassword";
        }

        userInformationService.updateUserPassword(newPasswordFirst);

        return "redirect:/list/todoList";
    }
}
