package com.todolist.dao;

import com.todolist.dto.UserInformationDto;
import com.todolist.entity.UserInformation;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface UserInformationDao {

    @Select
    UserInformation selectByUserName(String userName);

    @Select
    List<UserInformationDto> selectUserAll();

    @Update
    int update(UserInformation userInformation);

    @Insert(sqlFile = true)
    int insertUserInformation(UserInformation userInformation);
}
