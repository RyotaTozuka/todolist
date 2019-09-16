package com.todolist.service;

import com.todolist.dao.UserInformationDao;
import com.todolist.entity.UserInformation;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {
    @Autowired
    private UserInformationDao userInformationDao;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    public int updateUserPassword(String rawPassword) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        String encodedPassword = secureUserDetailsService.encodePassword(rawPassword);
        userInformation.setUserPassword(encodedPassword);

        return userInformationDao.update(userInformation);
    }
}
