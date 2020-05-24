package com.todolist.webApp.Header;

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
 * 管理者メイン画面のControllerクラス
 */
@Controller
public class HeaderController {

    @Autowired
    private AddParamUtil addParamUtil;

    @Autowired
    private CopyEntityToFormUtil copyEntityToFormUtil;

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
        addParamUtil.addMastAttribute(model);

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
        addParamUtil.addMastAttribute(model);

        //未完了のTodoリストの取得
        List<TodoListForm> todoListFormList = copyEntityToFormUtil.copyTodoListListToTodoListFormList(
                todoListService.getTodoListByUserIdAndFlag(secureUserDetailsService.getUserInformation().getUserId(), false));
        model.addAttribute("todoLists", todoListFormList);

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
        addParamUtil.addMastAttribute(model);
        return "user/changePassword";
    }
}