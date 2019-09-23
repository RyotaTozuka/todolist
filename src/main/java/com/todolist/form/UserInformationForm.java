package com.todolist.form;

import javax.validation.constraints.Size;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * UserInformation の Formクラス
 * user_information の持つ情報のうち、Viewに渡すものを抽出している。
 */
public class UserInformationForm {

    private Integer userId;

    @Size(min = 1, max = 20)
    private String userName;

    private String userRole;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
