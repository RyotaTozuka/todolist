package com.todolist.Util.ControllerUtil;

import com.todolist.entity.TodoList;
import com.todolist.form.TodoListForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
     * @param todoListForms　todoListFormリスト
     * @return todoListリスト
     */
    public List<TodoList> copyTodoListFormsToTodoLists(List<TodoListForm> todoListForms) {
        List<TodoList> todoLists = new ArrayList<>();

        for (TodoListForm todoListForm : todoListForms) {
            TodoList todoList = copyTodoListFormToTodoList(todoListForm);
            todoLists.add(todoList);
        }

        return todoLists;
    }

    public TodoList copyTodoListFormToTodoList(TodoListForm todoListForm) {
        TodoList todoList = new TodoList();
        BeanUtils.copyProperties(todoListForm,todoList);
        return todoList;
    }
}
