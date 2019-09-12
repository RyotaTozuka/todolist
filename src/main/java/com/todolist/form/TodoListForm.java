package com.todolist.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TodoListForm {

    @NotNull
    private Integer listId;

    @Size(max = 50)
    private String listContents;

    @Size(max = 8)
    private String listLimit;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
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
}
