package com.todolist.webApp.Redirect;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * リダイレクトの共通Controllerクラス
 */
@Controller
public class RedirectController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private UserInformationService userInformationService;

    @ModelAttribute
    TodoListForm setUpForm() {
        return new TodoListForm();
    }

    /**
     * Todoリスト画面
     *
     * @param model モデル
     * @return todoリスト画面
     */
    @RequestMapping("list/todoList")
    String todoList(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //未完了のTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), false);
        model.addAttribute("todoLists", todoListForms);

        return "list/todoList";
    }

    /**
     * 完了リスト画面
     *
     * @param model モデル
     * @return 完了リスト画面のアドレス
     */
    @RequestMapping("list/doneList")
    String mainComplete(Model model) {
        controllerProcedure.addMastAttribute(model);
        UserInformation userInformation = secureUserDetailsService.getUserInformation();

        //完了したTodoリストの取得
        List<TodoListForm> todoListForms = todoListService.getTodoListByUserIdAndFlag(userInformation.getUserId(), true);
        model.addAttribute("todoLists", todoListForms);

        return "list/doneList";
    }

    /**
     * 管理者メニュー画面
     *
     * @param model モデル
     * @return 管理者メニュー画面のアドレス
     */
    @RequestMapping("admin/main")
    public String mainAdmin(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "admin/main";
    }

    /**
     * ユーザ新規作成画面
     *
     * @param model モデル
     * @return ユーザ新規作成画面のアドレス
     */
    @RequestMapping("user/createUser")
    public String createUser(Model model) {

        controllerProcedure.addMastAttribute(model);
        UserInformationForm userInformationForm = new UserInformationForm();

        model.addAttribute("userInformationForm", userInformationForm);

        return "user/createUser";
    }

    /**
     * ユーザ情報一覧画面
     *
     * @param model モデル
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping("admin/userList")
    public String userList(Model model) {
        controllerProcedure.addMastAttribute(model);
        Integer userId = secureUserDetailsService.getUserInformation().getUserId();
        List<UserInformationForm> userInformationForms = userInformationService.selectUserAll();

        model.addAttribute("userLists", userInformationForms);
        model.addAttribute("userId", userId);

        return "admin/userList";
    }
}