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

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * TodoList の情報を扱うServiceクラス
 */
@Service
public class TodoListService {

    @Autowired
    private TodoListDao todoListDao;

    /**
     * userId情報に基づくTodoListを全件抽出する。
     * isCompleteフラグがマッチするものを抽出する。
     *
     * @param userId ユーザID
     * @param isComplete true：完了しているリスト、false：未完了のリスト
     * @return List<TodoListForm> の型で抽出、0件の場合は size=0 のListを返す
     */
    public List<TodoListForm> getTodoListByUserIdAndFlag(Integer userId, boolean isComplete) {
        List<TodoListDto> todoListDtos = todoListDao.selectTodoListByUserIdAndCompleteFlag(userId, isComplete);
        List<TodoListForm> todoListForms = new ArrayList<>();

        //Dto⇒Formクラスにデータコピー
        for (TodoListDto todoListDto : todoListDtos) {
            TodoListForm todoListForm = new TodoListForm();
            BeanUtils.copyProperties(todoListDto, todoListForm);
            todoListForms.add(todoListForm);
        }
        return todoListForms;
    }

    /**
     * 主キー:listId に紐づくTodoListを抽出する
     *
     * @param listId リストId
     * @return todoListForm の型で抽出
     */
    public TodoListForm getTodoListByListId(Integer listId) {
        TodoListDto todoListDto = todoListDao.selectTodoListByListId(listId);
        TodoListForm todoListForm = new TodoListForm();

        BeanUtils.copyProperties(todoListDto,todoListForm);

        return todoListForm;
    }

    /**
     * 入力されたTodoListの挿入
     *
     * @param todoList Todoリスト
     * @return 挿入成功件数
     */
    public int insertTodoList(TodoList todoList) {
        return todoListDao.insertTodoList(todoList);
    }

    /**
     * 入力されたlistIdに紐づくレコードの
     * ステータス（ListCompleteFlag）を未完了（false）から完了（true）にする
     *
     * @param listId リストId
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
    public int updateToCompleteByListId(Integer listId) {
        TodoList todoList = todoListDao.selectByListId(listId);
        todoList.setIsComplete(true);

        return todoListDao.update(todoList);
    }

    /**
     * 入力されたlistIdに紐づくレコードの
     * ステータス（ListCompleteFlag）を完了（true）から未完了（false）にする
     *
     * @param listId リストId
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
    public int updateToProcessingByListId(Integer listId) {
        TodoList todoList = todoListDao.selectByListId(listId);
        todoList.setIsComplete(false);

        return todoListDao.update(todoList);
    }

    /**
     * 入力された情報をもとにTodoListのレコード更新をする
     *
     * @param listId リストId
     * @param contents リストの内容
     * @param due リストの実施期限
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
    public int updateTodoList(Integer listId, String contents, String due) {
        TodoList todoList = todoListDao.selectByListId(listId);
        todoList.setContents(contents);
        todoList.setDue(due);

        return todoListDao.update(todoList);
    }

    /**
     * 入力されたlistIdに紐づくレコードを削除する
     *
     * @param listId リストId
     * @return 削除完了件数（1ならば正常、0ならば異常）
     */
    public int deleteListByListId(Integer listId) {
        TodoList todoList = new TodoList();
        todoList.setListId(listId);

       return todoListDao.delete(todoList);
    }

    /**
     * 入力されたuserIdに紐づく、ステータス：完了のレコードを全権削除
     *  (ListCompleteFlag = true: 完了、 false: 未完了)
     *
     * @param userId ログイン中のユーザId
     * @return 削除完了件数
     */
    public int deleteListByUserIdAndFlag(Integer userId, boolean isComplete) {
        TodoList todoList = new TodoList();
        todoList.setUserId(userId);
        todoList.setIsComplete(isComplete);

        return todoListDao.deleteListByUserIdAndCompleteFlag(todoList);
    }

    /**
     * 入力されたuserIdに紐づくレコードを全件削除
     *
     * @param userId ログイン中のユーザId
     * @return 削除完了件数
     */
    public int deleteListByUserId(Integer userId) {
        TodoList todoList = new TodoList();
        todoList.setUserId(userId);

        return todoListDao.deleteListByUserId(todoList);
    }
}