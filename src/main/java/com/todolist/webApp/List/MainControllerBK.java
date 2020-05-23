//package com.todolist.webApp.TodoList;
//
//import com.todolist.entity.TodoList;
//import com.todolist.entity.UserInformation;
//import com.todolist.form.TodoListForm;
//import com.todolist.security.SecureUserDetailsService;
//import com.todolist.service.TodoListService;
//import com.todolist.webAppCommon.ControllerProcedure;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * @author Ryota Tozuka
// * @version 0.0.1
// *
// * mainであるTodoリスト関連の画面のControllerクラス
// */
//@Controller
//public class MainControllerBK {
//
//    @Autowired
//    private TodoListService todoListService;
//
//    @Autowired
//    private SecureUserDetailsService secureUserDetailsService;
//
//    @Autowired
//    private ControllerProcedure controllerProcedure;
//
//    @ModelAttribute
//    TodoListForm setUpForm() {
//        return new TodoListForm();
//    }
//
//    /**
//     * 完了していないTodoリストを表示する画面に遷移する
//     *
//     * @param model モデル
//     * @return 未完了リスト画面のアドレス
//     */
//    @RequestMapping("main/processing")
//    String mainProcessing(Model model) {
//        controllerProcedure.addMastAttribute(model);
//        UserInformation userInformation = secureUserDetailsService.getUserInformation();
//
//        //未完了のTodoリストの取得
//        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), false);
//        model.addAttribute("todoLists", todoListForms);
//
//        return "main/processing";
//    }
//
//    /**
//     * 完了したTodoリストを表示する画面に遷移する
//     *
//     * @param model モデル
//     * @return 完了リスト画面のアドレス
//     */
//    @RequestMapping("main/complete")
//    String mainComplete(Model model) {
//        controllerProcedure.addMastAttribute(model);
//        UserInformation userInformation = secureUserDetailsService.getUserInformation();
//
//        //完了したTodoリストの取得
//        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), true);
//        model.addAttribute("todoLists", todoListForms);
//
//        return "main/complete";
//    }
//
//    /**
//     * Todoリストの編集画面に遷移する
//     *
//     * @param model モデル
//     * @return Todoリスト編集画面のアドレス
//     */
//    @RequestMapping("main/editing")
//    String mainEditing(Model model) {
//        controllerProcedure.addMastAttribute(model);
//
//        TodoListForm todoListForm = new TodoListForm();
//
//        //新規作成なので、isCreateにtrueを格納
//        model.addAttribute("isCreate", true);
//        model.addAttribute("todoListForm", todoListForm);
//
//        return "main/editing";
//    }
//
//    /**
//     * 選択したTodoリストのステータスを完了にし、
//     * 未完了リスト画面に遷移する
//     *
//     * @param listId 完了にするリストのlistId
//     * @return 未完了リスト画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "toComplete")
//    String completeTodoList(@RequestParam() Integer listId) {
//        todoListService.updateByListIdAndCompleteFlag(listId, true);
//
//        return "redirect:/main/processing";
//    }
//
//    /**
//     * 選択したTodoリストを編集するために、対象のlistIdの情報を保持したまま
//     * リスト編集画面に遷移する
//     *
//     * @param listId 編集対象のlistId
//     * @return リスト編集画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "edit")
//    String editTodoList(@RequestParam() Integer listId, Model model) {
//        controllerProcedure.addMastAttribute(model);
//
//        TodoListForm todoListForm = todoListService.getTodoListByListId(listId);
//
//        //新規登録ではないので、isCreateにfalseを格納
//        model.addAttribute("isCreate", false);
//        model.addAttribute("todoListForm", todoListForm);
//
//        return "main/editing";
//    }
//
//    /**
//     * 選択されたTodoリストを削除し、
//     * 削除実行前の画面に遷移する
//     *
//     * @param listId 削除対象のlistId
//     * @param status 遷移前画面の識別子
//     * @return 削除実行前の画面
//     */
//    @RequestMapping(value = "main/editTodoList", params = "delete")
//    String deleteTodoList(@RequestParam() Integer listId, @RequestParam() String status) {
//        todoListService.deleteListByListId(listId);
//
//        //完了リスト画面からの遷移の場合、statusに"complete"が入っている
//        //それ以外は、未完了リストから遷移したとみなす
//        if (("complete").matches(status)) {
//            return "redirect:/main/complete";
//        } else {
//            return "redirect:/main/processing";
//        }
//    }
//
//    /**
//     * 選択したTodoリストのステータスを未完了にし、
//     * 完了リスト画面に遷移する
//     *
//     * @param listId 未完了にするリストのlistId
//     * @return 完了リスト画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "toProcessing")
//    String processingTodoList(@RequestParam() Integer listId) {
//        todoListService.updateByListIdAndCompleteFlag(listId, false);
//
//        return "redirect:/main/complete";
//    }
//
//    /**
//     * TOdoリストの新規登録の処理を実行した後、
//     * 未完了リスト画面に遷移
//     *
//     * @param form 入力のTodoリストフォーム
//     * @param result エラー格納変数
//     * @param model モデル
//     * @return 未完了リスト画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "insert")
//    String insertTodoList(@ModelAttribute("todoListForm") @Valid TodoListForm form,
//                          BindingResult result,
//                          @ModelAttribute("isCreate") boolean isCreate,
//                          Model model) {
//        controllerProcedure.addMastAttribute(model);
//
//        if (result.hasErrors()) {
//            return "main/editing";
//        }
//
//        //todoリストの新規作成
//        TodoList todoList = new TodoList();
//
//        todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
//        todoList.setContents(form.getContents());
//        todoList.setDue(form.getDue());
//        todoList.setIsComplete(false);
//
//        todoListService.insertTodoList(todoList);
//
//        return "redirect:/main/processing";
//    }
//
//    /**
//     * 入力された情報をもとにTodoリストを編集し、
//     * 未完了リスト画面に遷移する
//     *
//     * @param form 入力のTodoリストフォーム
//     * @param result エラー格納変数
//     * @param model モデル
//     * @return 未完了リスト画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "update")
//    String editTodoList(@ModelAttribute("todoListForm") @Valid TodoListForm form,
//                        BindingResult result,
//                        @ModelAttribute("isCreate") boolean isCreate,
//                        Model model) {
//        controllerProcedure.addMastAttribute(model);
//
//        if (result.hasErrors()) {
//            return "main/editing";
//        }
//
//        todoListService.updateTodoList(form.getListId(), form.getContents(), form.getDue());
//
//        return "redirect:/main/processing";
//    }
//
//    /**
//     * 完了しているTodoリストをすべて削除し、
//     * 未完了リスト画面に遷移する
//     *
//     * @return 未完了リスト画面のアドレス
//     */
//    @RequestMapping(value = "main/editTodoList", params = "deleteCompleteAll")
//    String deleteAllCompleteList() {
//        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
//
//        //ログインしているユーザに紐づく全完了リストを削除
//        todoListService.deleteListByUserIdAndFlag(userId, true);
//
//        return "redirect:/main/processing";
//    }
//}