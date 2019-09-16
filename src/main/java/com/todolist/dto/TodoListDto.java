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
    private String listContents;
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
