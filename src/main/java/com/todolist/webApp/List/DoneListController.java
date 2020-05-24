package com.todolist.webApp.List;

import com.todolist.Util.ControllerUtil.CopyEntityToFormUtil;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import com.todolist.Util.ControllerUtil.AddParamUtil;
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
public class DoneListController {

    @Autowired
    private AddParamUtil addParamUtil;

    @Autowired
    private CopyEntityToFormUtil copyEntityToFormUtil;

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @ModelAttribute
    TodoListForm setUpForm() {
        return new TodoListForm();
    }

    /**
     * TOdoリストに戻るボタン押下
     *
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("list/doneList/todoList")
    String mainProcessing(Model model) {
        addParamUtil.addMastAttribute(model);

        //未完了のTodoリストの取得
        List<TodoListForm> todoListFormList = copyEntityToFormUtil.copyTodoListListToTodoListFormList (
                todoListService.getTodoListByUserIdAndFlag(secureUserDetailsService.getUserInformation().getUserId(), false));

        model.addAttribute("todoLists", todoListFormList);

        return "list/todoList";
    }

    /**
     * 削除ボタン押下
     *
     * @param listId 削除対象のlistId
     * @param status 遷移前画面の識別子
     * @return 削除実行前の画面
     */
    @RequestMapping("list/doneList/delete")
    String deleteTodoList(@RequestParam() Integer listId, @RequestParam() String status) {
        todoListService.deleteListByListId(listId);

        return "redirect:/list/doneList";
    }

    /**
     * 未完了にするボタン押下
     *
     * @param listId 未完了にするリストのlistId
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping("list/doneList/todo")
    String processingTodoList(@RequestParam() Integer listId) {
        todoListService.updateByListIdAndCompleteFlag(listId, false);

        return "redirect:/list/doneList";
    }

    /**
     * すべて削除ボタン押下
     *
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping(value = "list/doneList/deleteAll")
    String deleteAllCompleteList() {

        //ログインしているユーザに紐づく全完了リストを削除
        todoListService.deleteListByUserIdAndFlag(secureUserDetailsService.getUserInformation().getUserId(), true);

        return "redirect:/list/doneList";
    }
}