package com.todolist.form;

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
    private String contents;

    @Size(max = 10)
    private String due;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
