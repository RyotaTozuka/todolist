package com.todolist.dao;

import com.todolist.dto.TodoListDto;
import com.todolist.entity.TodoList;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface TodoListDao {

    @Select
    List<TodoListDto> selectProcessingListByUserId(Integer userId);

    @Select
    List<TodoListDto> selectCompleteListByUserId(Integer userId);

    @Select TodoListDto selectTodoListByListId(Integer listId);

    @Insert(sqlFile = true)
    int insertTodoList(TodoList todoList);

    @Update
    int update(TodoList todoList);
}