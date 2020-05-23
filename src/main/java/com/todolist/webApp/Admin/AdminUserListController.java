package com.todolist.webApp.Admin;

import com.todolist.service.TodoListService;
import com.todolist.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * 管理者画面のControllerクラス
 */
@Controller
public class AdminUserListController {

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private TodoListService todoListService;

    /**
     * ユーザ情報一覧画面にて選択されたユーザの管理者権限を変更し
     * ユーザ情報一覧画面に遷移する
     *
     * @param changeRoleId 権限情報を更新する対象のuserId
     * @return ユーザ情報一覧画面のアドレス
     */
    @RequestMapping(value="admin/userList/editUserList", params="changeRoleId")
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
    @RequestMapping(value="admin//userList/editUserList", params="deleteId")
    public String deleteUser(@RequestParam() Integer deleteId) {
        //削除対象のユーザに紐づくtodoリストの内容も削除する
        todoListService.deleteListByUserId(deleteId);
        userInformationService.deleteUserByUserId(deleteId);

        return "redirect:/admin/userList";
    }
}