package com.todolist.service;

import com.todolist.dao.TodoListDao;
import com.todolist.dto.TodoListDto;
import com.todolist.entity.TodoList;
import com.todolist.form.TodoListForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListService {
    @Autowired
    private TodoListDao todoListDao;

    public List<TodoListForm> getTodoListByUserId(Integer userId) {
        List<TodoListForm> todoListForms = new ArrayList<TodoListForm>();
        List<TodoListDto> todoListDtos = todoListDao.selectProcessingListByUserId(userId);

        for (TodoListDto todoListDto : todoListDtos) {
            TodoListForm todoListForm = new TodoListForm();
            BeanUtils.copyProperties(todoListDto, todoListForm);
            todoListForms.add(todoListForm);
        }
        return todoListForms;
    }

    public List<TodoListForm> getCompleteListByUserId(Integer userId) {
        List<TodoListForm> todoListForms = new ArrayList<TodoListForm>();
        List<TodoListDto> todoListDtos = todoListDao.selectCompleteListByUserId(userId);

        for (TodoListDto todoListDto : todoListDtos) {
            TodoListForm todoListForm = new TodoListForm();
            BeanUtils.copyProperties(todoListDto, todoListForm);
            todoListForms.add(todoListForm);
        }
        return todoListForms;
    }

    public int insertTodoList(TodoList todoList) {
        return todoListDao.insertTodoList(todoList);
    }
}