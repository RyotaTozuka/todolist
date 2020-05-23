package com.todolist.webApp.List;

import com.todolist.entity.TodoList;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import com.todolist.webAppCommon.ControllerProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * mainであるTodoリスト関連の画面のControllerクラス
 */
@Controller
public class EditListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @ModelAttribute
    TodoListForm setUpForm() {
        return new TodoListForm();
    }

    /**
     * 編集ボタン押下
     *
     * @param form 入力のTodoリストフォーム
     * @param result エラー格納変数
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("list/editList/edit")
    String editTodoList(@ModelAttribute("todoListForm") @Valid TodoListForm form,
                        BindingResult result,
                        @ModelAttribute("isCreate") boolean isCreate,
                        Model model) {
        controllerProcedure.addMastAttribute(model);

        if (result.hasErrors()) {
            return "main/editing";
        }
        //todo:insert or updateの処理検討
        if (isCreate) {
            //todoリストの新規作成
            TodoList todoList = new TodoList();

            todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
            todoList.setContents(form.getContents());
            todoList.setDue(form.getDue());
            todoList.setIsComplete(false);

            todoListService.insertTodoList(todoList);
        } else  {
            todoListService.updateTodoList(form.getListId(), form.getContents(), form.getDue());
        }

        return "redirect:/list/todoList";
    }
}