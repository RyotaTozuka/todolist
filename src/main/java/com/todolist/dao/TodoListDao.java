package com.todolist.dao;

import com.todolist.dto.TodoListDto;
import com.todolist.entity.TodoList;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * DB: todo_list とやり取りするdaoクラス
 * FWとしてdomaを使用
 * selectすべて及び、update,insert,deleteのうち sqlFile=trueのものは
 * target classの META-INF/com/todolist/dao/TodoListDao 配下にsqlファイルがある
 */
@ConfigAutowireable
@Dao
public interface TodoListDao {

    @Select
    List<TodoListDto> selectTodoListByUserIdAndCompleteFlag(Integer userId, boolean isComplete);

    @Select
    TodoListDto selectTodoListByListId(Integer listId);

    @Select
    TodoList selectByListId(Integer listId);

    @Insert(sqlFile = true)
    int insertTodoList(TodoList todoList);

    @Update
    int update(TodoList todoList);

    @Delete
    int delete(TodoList todoList);

    @Delete(sqlFile = true)
    int deleteListByUserIdAndCompleteFlag(TodoList todoList);

    @Delete(sqlFile = true)
    int deleteListByUserId(TodoList todoList);
}