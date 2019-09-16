package com.todolist.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * UserInformation のDtoクラス
 * user_information の持つ情報のうち、Viewに渡すものを抽出している。
 */
@Entity
public class UserInformationDto {

    @Id
    private Integer userId;
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
