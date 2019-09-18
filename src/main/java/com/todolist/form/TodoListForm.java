package com.todolist.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * TodoListの Formクラス
 * todo_list の持つ情報のうち、Viewに渡すものを抽出している。
 */
public class TodoListForm {

    private Integer listId;

    @Size(min = 1, max = 30)
    private String listContents;

    @Size(max = 10)
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
