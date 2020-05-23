package com.todolist.webAppCommon;

import com.todolist.entity.UserInformation;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Controllerクラスでの共通処理
 */
@Component
public class ControllerProcedure {
    //権限情報の定義：thymeleafで扱いやすくするため、true/falseに変換
    private static final boolean ROLE_ADMIN = true;
    private static final boolean ROLE_USER = false;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    /**
     * 画面遷移時に常に必要にる情報をModelに格納し
     * 情報をviewに渡す
     *
     * @param model モデル
     */
    public void addMastAttribute(Model model) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //ヘッダに表示するためのユーザ名
        model.addAttribute("userName", userInformation.getUserName());

        //ヘッダの管理者用リンクの表示条件に使用する、権限情報
        if (("ROLE_ADMIN").matches(userInformation.getUserRole())) {
            model.addAttribute("userRole", ROLE_ADMIN);
        } else {
            model.addAttribute("userRole", ROLE_USER);
        }
    }
}
