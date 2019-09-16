package com.todolist.entity;

import org.seasar.doma.*;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * TodoList の Entityクラス
 */
@Entity
@Table(name = "todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listId;
    private Integer userId;
    private String listContents;
    private String listLimit;
    private boolean listCompleteFlag;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
