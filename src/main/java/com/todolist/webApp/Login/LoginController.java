package com.todolist.webApp.Login;

import com.todolist.Util.ControllerUtil.CopyEntityToFormUtil;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import com.todolist.Util.ControllerUtil.AddParamUtil;
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
    private AddParamUtil addParamUtil;

    @Autowired
    private CopyEntityToFormUtil copyEntityToFormUtil;

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
        addParamUtil.addMastAttribute(model);

        //未完了のTodoリストの取得
        List<TodoListForm> todoListForms = copyEntityToFormUtil.copyTodoListListToTodoListFormList(
                todoListService.getTodoListByUserIdAndFlag(secureUserDetailsService.getUserInformation().getUserId(), false));
        model.addAttribute("todoLists", todoListForms);

        return "list/todoList";
    }
}