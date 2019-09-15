package com.todolist.dao;

import com.todolist.dto.TodoListDto;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface TodoListDao {

    @Select
    List<TodoListDto> selectProcessingListByUserId(Integer userId);

    @Select
    List<TodoListDto> selectCompleteListByUserId(Integer userId);
}