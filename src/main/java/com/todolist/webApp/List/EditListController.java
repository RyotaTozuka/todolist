package com.todolist.webApp.List;

import com.todolist.Util.ControllerUtil.CopyFormToEntityUtil;
import com.todolist.entity.TodoList;
import com.todolist.form.TodoListForm;
import com.todolist.service.TodoListService;
import com.todolist.Util.ControllerUtil.AddParamUtil;
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
    private AddParamUtil addParamUtil;

    @Autowired
    private CopyFormToEntityUtil copyFormToEntityUtil;

    @Autowired
    private TodoListService todoListService;

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
        addParamUtil.addMastAttribute(model);

        if (result.hasErrors()) {
            return "list/editList";
        }

        TodoList todoList = copyFormToEntityUtil.copyTodoListFormToTodoList(form);

        if (isCreate) {
            //todoリストの新規作成
            todoListService.insertTodoList(todoList);
        } else  {
            todoListService.updateTodoList(todoList);
        }

        return "redirect:/list/todoList";
    }
}