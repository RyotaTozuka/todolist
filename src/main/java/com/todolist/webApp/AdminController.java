package com.todolist.webApp;

import com.todolist.form.UserInformationForm;
import com.todolist.service.TodoListService;
import com.todolist.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * 管理者画面のControllerクラス
 */
@Controller
public class AdminController {

    @Autowired
    private ControllerProcedure controllerProcedure;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private TodoListService todoListService;

    /**
     * 管理者メニュー画面へ遷移する
     *
     * @param model モデル
     * @return 管理者メニュー画面のアドレス
     */
    @RequestMapping("/admin/main")
    public String mainAdmin(Model model) {
        controllerProcedure.addMastAttribute(model);
        return "/admin/main";
    }

    /**
     * ユーザ情報一覧画面へ遷移する
     *
     * @param model モデル
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping("/admin/userList")
    public String userList(Model model) {
        controllerProcedure.addMastAttribute(model);

        List<UserInformationForm> userInformationForms = userInformationService.selectUserAll();
        model.addAttribute("userLists", userInformationForms);

        return "/admin/userList";
    }

    /**
     * ユーザ情報一覧画面にて選択されたユーザの管理者権限を変更し
     * ユーザ情報一覧画面に遷移する
     *
     * @param changeRoleId 権限情報を更新する対象のuserId
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping(value="/admin/editUserList", params="changeRoleId")
    public String changeRole(@RequestParam() Integer changeRoleId) {
        userInformationService.flipUserRoleByUserId(changeRoleId);

        return "redirect:/admin/userList";
    }

    /**
     * ユーザ情報一覧画面にて選択されたユーザを削除し
     * ユーザ情報一覧画面に遷移する
     *
     * @param deleteId 削除対象のuserId
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping(value="/admin/editUserList", params="deleteId")
    public String deleteUser(@RequestParam() Integer deleteId) {
        todoListService.deleteListByUserId(deleteId);
        userInformationService.deleteUserByUserId(deleteId);

        return "redirect:/admin/userList";
    }
}
