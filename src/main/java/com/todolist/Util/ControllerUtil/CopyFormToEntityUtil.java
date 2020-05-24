package com.todolist.Util.ControllerUtil;

import com.todolist.entity.TodoList;
import com.todolist.form.TodoListForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Form⇒Entity変換用utilクラス
 */
@Component
public class CopyFormToEntityUtil {

   /**
     * todoListFormリスト⇒todoListリスト
     *
     * @param todoListFormList　todoListFormリスト
     * @return todoListリスト
     */
    public List<TodoList> copyTodoListFormListToTodoListList(List<TodoListForm> todoListFormList) {
        List<TodoList> todoListList = new ArrayList<>();

        for (TodoListForm todoListForm : todoListFormList) {
            TodoList todoList = copyTodoListFormToTodoList(todoListForm);
            todoListList.add(todoList);
        }

        return todoListList;
    }

    public TodoList copyTodoListFormToTodoList(TodoListForm todoListForm) {
        TodoList todoList = new TodoList();
        BeanUtils.copyProperties(todoListForm,todoList);
        return todoList;
    }
}
