package com.todolist.entity;


import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import javax.validation.constraints.NotNull;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * UserInformation の Entityクラス
 */
@Entity
@Table(name = "user_information")
public class UserInformation {

    @Id private Integer userId;
    private String userName;
    private String userPassword;
    @NotNull
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
