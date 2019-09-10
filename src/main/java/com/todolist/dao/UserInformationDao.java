package com.todolist.dao;

import com.todolist.entity.UserInformation;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface UserInformationDao {

    @Select
    UserInformation selectByUserName(String userName);
}
