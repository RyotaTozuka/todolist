package com.todolist.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
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
