package com.todolist.Util.ServiceUtil;

import com.todolist.dto.TodoListDto;
import com.todolist.dto.UserInformationDto;
import com.todolist.entity.TodoList;
import com.todolist.entity.UserInformation;
import com.todolist.form.TodoListForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Orm⇒Entity変換用utilクラス
 */
@Component
public class CopyOrmToEntityUtil {

    /**
     * ユーザ情報Dto⇒ユーザ情報リスト
     *
     * @param userInformationDtoList ユーザ情報Dtoリスト
     * @return ユーザ情報
     */
    public List<UserInformation> copyUserInformationDtoListToUserInformationList(List<UserInformationDto> userInformationDtoList) {
        List<UserInformation> userInformationList = new ArrayList<>();

        for (UserInformationDto userInformationDto : userInformationDtoList) {
            UserInformation userInformation = copyUserInformationDtoToUserInformation(userInformationDto);
            userInformationList.add(userInformation);
        }

        return userInformationList;
    }

    /**
     * ユーザ情報Dto⇒ユーザ情報
     *
     * @param userInformationDto ユーザ情報Dto
     * @return ユーザ情報
     */
    public UserInformation copyUserInformationDtoToUserInformation(UserInformationDto userInformationDto) {
        UserInformation userInformation = new UserInformation();
        BeanUtils.copyProperties(userInformationDto, userInformation);
        return userInformation;
    }

    /**
     *
     *
     * @param todoListDto
     * @return
     */
    public TodoList copyTodoListDtoToTodoList(TodoListDto todoListDto) {
        TodoList todoList = new TodoList();
        BeanUtils.copyProperties(todoListDto,todoList);
        return todoList;
    }

}
