package com.todolist.webApp;

import com.todolist.entity.TodoList;
import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import com.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * mainであるTodoリスト関連の画面のControllerクラス
 */
@Controller
public class MainController {

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
     * 完了していないTodoリストを表示する画面に遷移する
     *
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping("/main/processing")
    String mainProcessing(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //未完了のTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/processing";
    }

    /**
     * 完了したTodoリストを表示する画面に遷移する
     *
     * @param model モデル
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping("/main/complete")
    String mainComplete(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //完了したTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getCompleteListByUserId(userInformation.getUserId());
        model.addAttribute("todoLists", todoListForms);
        return "/main/complete";
    }

    /**
     * Todoリストの編集画面に遷移する
     *
     * @param isCreate true:新規登録、false:既存リストの編集（更新）
     * @param listId TodoリストのId
     * @param model モデル
     * @return Todoリスト編集画面のアドレス
     */
    @RequestMapping("/main/editing")
    String mainEditing(@RequestParam() boolean isCreate, @RequestParam(defaultValue = "0") Integer listId, Model model) {
        controllerProcedure.addMastAttribute(model);

        TodoListForm todoListForm = new TodoListForm();

        if (!isCreate) {
            todoListForm = todoListService.getTodoListByListId(listId);
        }
        model.addAttribute("todoListForm", todoListForm);
        model.addAttribute("isCreate", isCreate);

        return "/main/editing";
    }

    /**
     * TOdoリストの新規登録の処理を実行した後、
     * 未完了リスト画面に遷移
     *
     * @param form 入力のTodoリストフォーム
     * @param result エラー格納変数
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "insert")
    String insertTodoList(@ModelAttribute("todoListForm") @Valid TodoListForm form,
                          BindingResult result,
                          @RequestParam("isCreate") boolean isCreate,
                          Model model) {
        controllerProcedure.addMastAttribute(model);

        if (result.hasErrors()) {
            model.addAttribute("isCreate", isCreate);
            model.addAttribute("todoListForm", form);
            return "/main/editing";
        }

        TodoList todoList = new TodoList();

        todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
        todoList.setListContents(form.getListContents());
        todoList.setListLimit(form.getListLimit());

        todoListService.insertTodoList(todoList);

        return "redirect:/main/processing";
    }

    /**
     * 選択したTodoリストのステータスを完了にし、
     * 未完了リスト画面に遷移する
     *
     * @param completeId 完了にするリストのlistId
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "completeId")
    String completeTodoList(@RequestParam() Integer completeId) {
        todoListService.updateToCompleteByListId(completeId);

        return "redirect:/main/processing";
    }

    /**
     * 選択したTodoリストのステータスを未完了にし、
     * 完了リスト画面に遷移する
     *
     * @param processingId 未完了にするリストのlistId
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "processingId")
    String processingTodoList(@RequestParam() Integer processingId) {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        todoListService.updateToProcessingByListId(processingId);

        return "redirect:/main/complete";
    }

    /**
     * 選択したTodoリストを編集するために、対象のlistIdの情報を保持したまま
     * リスト編集画面に遷移する
     *
     * @param editId 編集対象のlistId
     * @param redirectAttribute listIdを格納
     * @return リスト編集画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "editId")
    String editTodoList(@RequestParam() Integer editId, RedirectAttributes redirectAttribute) {
        redirectAttribute.addAttribute("isCreate", false);
        redirectAttribute.addAttribute("listId", editId);

        return "redirect:/main/editing";
    }

    /**
     * 入力された情報をもとにTodoリストを編集し、
     * 未完了リスト画面に遷移する
     *
     * @param form 入力のTodoリストフォーム
     * @param result エラー格納変数
     * @param model モデル
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "update")
    String editTodoList(@ModelAttribute("todoListForm") @Valid TodoListForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            controllerProcedure.addMastAttribute(model);
            model.addAttribute("todoListForm", form);
            return "/main/editing";
        }

        todoListService.updateTodoList(form.getListId(), form.getListContents(), form.getListLimit());

        return "redirect:/main/processing";
    }

    /**
     * 選択されたTodoリストを削除し、
     * 削除実行前の画面に遷移する
     *
     * @param deleteId 削除対象のlistId
     * @param status 遷移前画面の識別子
     * @return 削除実行前の画面
     */
    @RequestMapping(value = "/main/editTodoList", params = "deleteId")
    String deleteTodoList(@RequestParam() Integer deleteId, @RequestParam(defaultValue = "NO_STATE") String status) {
        todoListService.deleteTodoList(deleteId);

        /*完了リスト画面からの遷移の場合、statusに"complete"が入っている
        それ以外は、未完了リストから遷移したとみなす */
        if (("complete").matches(status)) {
            return "redirect:/main/complete";
        } else {
            return "redirect:/main/processing";
        }
    }

    /**
     * 完了しているTodoリストをすべて削除し、
     * 未完了リスト画面に遷移する
     *
     * @return 未完了リスト画面のアドレス
     */
    @RequestMapping(value = "/main/editTodoList", params = "deleteCompleteAll")
    String deleteAllCompleteList() {
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();

        //ログインしているユーザに紐づく全完了リストを削除
        todoListService.deleteAllCompleteListByUserId(userId);

        return "redirect:/main/processing";
    }
}