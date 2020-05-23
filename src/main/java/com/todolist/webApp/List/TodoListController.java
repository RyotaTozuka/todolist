package com.todolist.webApp.List;

import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import com.todolist.webAppCommon.ControllerProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 * <p>
 * mainであるTodoリスト関連の画面のControllerクラス
 */
@Controller
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    @ModelAttribute
    TodoListForm setUpForm() {
        return new TodoListForm();
    }

    /**
     * 完了したものを表示リンク押下
     *
     * @param model モデル
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping("list/todoList/doneList")
    String mainComplete(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //完了したTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), true);
        model.addAttribute("todoLists", todoListForms);

        return "list/doneList";
    }

    /**
     * 　新規登録ボタン押下
     *
     * @param model モデル
     * @return Todoリスト編集画面のアドレス
     */
    @RequestMapping("list/todoList/create")
    String mainEditing(Model model) {
        controllerProcedure.addMastAttribute(model);

        TodoListForm todoListForm = new TodoListForm();

        //新規作成なので、isCreateにtrueを格納
        model.addAttribute("isCreate", true);
        model.addAttribute("todoListForm", todoListForm);

        return "list/editList";
    }

    /**
     * 完了ボタン押下
     *
     * @param listId 完了にするリストのlistId
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("list/todoList/done")
    String completeTodoList(@RequestParam() Integer listId) {
        todoListService.updateByListIdAndCompleteFlag(listId, true);

        return "redirect:/list/todoList";
    }

    /**
     * 編集ボタン押下
     *
     * @param listId 編集対象のlistId
     * @return リスト編集画面のアドレス
     */
    @RequestMapping("list/todoList/edit")
    String editTodoList(@RequestParam() Integer listId, Model model) {
        controllerProcedure.addMastAttribute(model);

        TodoListForm todoListForm = todoListService.getTodoListByListId(listId);

        //新規登録ではないので、isCreateにfalseを格納
        model.addAttribute("isCreate", false);
        model.addAttribute("todoListForm", todoListForm);

        return "list/editList";
    }

    /**
     * 削除ボタン押下
     *
     * @param listId 削除対象のlistId
     * @param status 遷移前画面の識別子
     * @return 削除実行前の画面
     */
    @RequestMapping("list/todoList/delete")
    String deleteTodoList(@RequestParam() Integer listId, @RequestParam() String status) {
        todoListService.deleteListByListId(listId);

        return "redirect:/list/todoList";
    }
}