package com.todolist.security;

import com.todolist.dao.UserInformationDao;
import com.todolist.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecureUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInformationDao userInformationDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInformation userInformation = userInformationDao.selectByUserName(userName);

        if (userInformation == null) {
            throw new UsernameNotFoundException("ユーザ:" + userName + "は存在しません");
        }

        return new SecureUserDetails(userInformation);
    }
}
