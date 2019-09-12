package com.todolist.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "todo_list")
public class TodoList {

    @Id private Integer listId;
    private String userId;
    private String listContents;
    private String listLimit;
    private boolean listCompleteFlag;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListContents() {
        return listContents;
    }

    public void setListContents(String listContents) {
        this.listContents = listContents;
    }

    public String getListLimit() {
        return listLimit;
    }

    public void setListLimit(String listLimit) {
        this.listLimit = listLimit;
    }

    public boolean isListCompleteFlag() {
        return listCompleteFlag;
    }

    public void setListCompleteFlag(boolean listCompleteFlag) {
        this.listCompleteFlag = listCompleteFlag;
    }
}
