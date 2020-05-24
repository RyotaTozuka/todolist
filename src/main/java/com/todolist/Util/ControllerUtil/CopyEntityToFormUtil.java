package com.todolist.Util.ControllerUtil;

import com.todolist.entity.TodoList;
import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import com.todolist.form.UserInformationForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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

    /**
     * todoListリスト⇒todoListFormリスト
     *
     * @param todoListList　todoListリスト
     * @return todoListFormリスト
     */
    public List<TodoListForm> copyTodoListListToTodoListFormList(List<TodoList> todoListList) {
        List<TodoListForm> todoListFormList = new ArrayList<>();

        for (TodoList todoList : todoListList) {
            TodoListForm todoListForm = copyTodoListToTodoListForm(todoList);
            todoListFormList.add(todoListForm);
        }

        return todoListFormList;
    }

    public List<UserInformationForm> copyUserInformationListToUserInformationFormList(List<UserInformation> userInformationList) {
        List<UserInformationForm> userInformationFormList = new ArrayList<>();

        for (UserInformation userInformation : userInformationList) {
            UserInformationForm userInformationForm = copyUserInformationToUserInformationForm(userInformation);
            userInformationFormList.add(userInformationForm);
        }

        return userInformationFormList;
    }

    public UserInformationForm copyUserInformationToUserInformationForm(UserInformation userInformation) {
        UserInformationForm userInformationForm = new UserInformationForm();
        BeanUtils.copyProperties(userInformation,userInformationForm);
        return userInformationForm;
    }

    public TodoListForm copyTodoListToTodoListForm(TodoList todoList) {
        TodoListForm todoListForm = new TodoListForm();
        BeanUtils.copyProperties(todoList,todoListForm);
        return todoListForm;
    }
}
