package com.todolist.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * TodoListのDtoクラス
 * todo_list の持つ情報のうち、Viewに渡すものを抽出している。
 */
@Entity
public class TodoListDto {
    @Id private Integer listId;
    private String contents;
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
