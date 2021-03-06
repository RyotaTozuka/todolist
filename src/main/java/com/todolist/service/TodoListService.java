package com.todolist.service;

import com.todolist.Util.ServiceUtil.CopyOrmToEntityUtil;
import com.todolist.dao.TodoListDao;
import com.todolist.dto.TodoListDto;
import com.todolist.entity.TodoList;
import com.todolist.security.SecureUserDetailsService;
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

    //期限なし定数：期限が空白の場合、9999-21-31を入れる
    private static final String NO_LIMIT = "9999-12-31";

    @Autowired
    private CopyOrmToEntityUtil copyOrmToEntityUtil;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

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
    public List<TodoList> getTodoListByUserIdAndFlag(Integer userId, boolean isComplete) {
        List<TodoListDto> todoListDtoList = todoListDao.selectTodoListByUserIdAndCompleteFlag(userId, isComplete);
        List<TodoList> todoListList = new ArrayList<>();

        //Dto⇒todoListエンティティにデータコピー
        for (TodoListDto todoListDto : todoListDtoList) {
            TodoList todoList = new TodoList();
            BeanUtils.copyProperties(todoListDto, todoList);
            todoListList.add(todoList);
        }
        return todoListList;
    }

    /**
     * 主キー:listId に紐づくTodoListを抽出する
     *
     * @param listId リストId
     * @return todoListForm の型で抽出
     */
    public TodoList getTodoListByListId(Integer listId) {
        TodoListDto todoListDto = todoListDao.selectTodoListByListId(listId);
        return copyOrmToEntityUtil.copyTodoListDtoToTodoList(todoListDto);
    }

    /**
     * 入力されたTodoListの挿入
     *
     * @param todoList Todoリスト
     * @return 挿入成功件数
     */
    public int insertTodoList(TodoList todoList) {
        todoList.setUserId(secureUserDetailsService.getUserInformation().getUserId());
        todoList.setIsComplete(false);
        todoList.setDue(setDueWithFiniteRange(todoList.getDue()));

        return todoListDao.insertTodoList(todoList);
    }

    /**
     * 入力されたlistIdに紐づくレコードの
     * ステータス（ListCompleteFlag）を入力されたものに変更する
     *
     * @param listId リストId
     * @param isComplete true：完了済み。false：未完了
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
    public int updateByListIdAndCompleteFlag(Integer listId, boolean isComplete) {
        TodoList todoList = todoListDao.selectByListId(listId);
        todoList.setIsComplete(isComplete);

        return todoListDao.update(todoList);
    }

    /**
     * 入力された情報をもとにTodoListのレコード更新をする
     *
    * @param todoListDiff todoListの更新データ
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
//    public int updateTodoList(Integer listId, String contents, String due) {
    public int updateTodoList(TodoList todoListDiff) {

        TodoList todoList = todoListDao.selectByListId(todoListDiff.getListId());
        todoList.setContents(todoListDiff.getContents());
        todoList.setDue(setDueWithFiniteRange(todoListDiff.getDue()));

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
     * 入力されたuserIdに紐づく、ステータス：完了のレコードを全件削除
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

    //期限が指定なしの場合は、NO_LIMITを入れる
    private String setDueWithFiniteRange(String due) {
        if ("".matches(due)) {
            due = NO_LIMIT;
        }
        return due;
    }
}