package com.todolist.Util.ControllerUtil;

import com.todolist.entity.TodoList;
import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * entity⇔Form変換用utilクラス
 */
@Component
public class CopyEntityToFormUtil {
    //権限情報の定義：thymeleafで扱いやすくするため、true/falseに変換
    private static final boolean ROLE_ADMIN = true;
    private static final boolean ROLE_USER = false;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    /**
     * todoListリスト⇒todoListFormリスト
     *
     * @param todoLists　todoListリスト
     * @return todoListFormリスト
     */
    public List<TodoListForm> copyTodoListsToTodoListForms(List<TodoList> todoLists) {
        List<TodoListForm> todoListForms = new ArrayList<>();

        for (TodoList todoList : todoLists) {
            TodoListForm todoListForm = new TodoListForm();
            BeanUtils.copyProperties(todoList, todoListForm);
            todoListForms.add(todoListForm);
        }

        return todoListForms;
    }
}
