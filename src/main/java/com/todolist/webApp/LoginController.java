package com.todolist.webApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * login処理用のControllerクラス
 */
@Controller
public class LoginController {

    /**
     * ログイン画面に遷移する
     * ※Spring Securityでログイン処理時にこのアドレスを参照する
     *
     * @link com.todolist.SecurityConfig
     * @return ログイン画面のアドレス
     */
    @RequestMapping("login")
    String login() {
        return "login";
    }
}