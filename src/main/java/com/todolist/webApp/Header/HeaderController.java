package com.todolist.webApp.Header;

import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
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
public class HeaderController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private TodoListService todoListService;

    /**
     * 管理者メニュー画面リンク押下
     *
     * @param model モデル
     * @return 管理者メニュー画面のアドレス
     */
    @RequestMapping("/header/adminMain")
    public String mainAdmin(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "admin/main";
    }

    /**
     * Todoリスト画面リンク押下
     *
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("header/todoList")
    String mainProcessing(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //未完了のTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), false);
        model.addAttribute("todoLists", todoListForms);

        return "list/todoList";
    }

    /**
     * パスワード変更リンク押下
     *
     * @param model モデル
     * @return パスワード変更画面のアドレス
     */
    @RequestMapping("header/changePassword")
    public String changePassword(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "user/changePassword";
    }
}