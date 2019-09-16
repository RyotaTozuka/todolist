package com.todolist.dao;

import com.todolist.dto.UserInformationDto;
import com.todolist.entity.UserInformation;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * DB: user_information とやり取りするdaoクラス
 * FWとしてdomaを使用
 * selectすべて及び、update,insert,deleteのうち sqlFile=trueのものは
 * target classの META-INF/com/todolist/dao/UserInformationDao 配下にsqlファイルがある
 */
@ConfigAutowireable
@Dao
public interface UserInformationDao {

    @Select
    UserInformation selectByUserName(String userName);

    @Select
    UserInformation selectByUserId(Integer userId);

    @Select
    List<UserInformationDto> selectUserAll();

    @Update
    int update(UserInformation userInformation);

    @Insert(sqlFile = true)
    int insertUserInformation(UserInformation userInformation);

    @Delete
    int delete(UserInformation userInformation);
}
