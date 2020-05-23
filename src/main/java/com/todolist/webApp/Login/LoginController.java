package com.todolist.webApp.Login;

import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
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
 * login処理用のControllerクラス
 */
@Controller
public class LoginController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

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

    /**
     * ログインボタン押下（ログイン成功）
     *
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("login/todoList")
    String mainProcessing(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //未完了のTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), false);
        model.addAttribute("todoLists", todoListForms);

        return "main/processing";
    }
}