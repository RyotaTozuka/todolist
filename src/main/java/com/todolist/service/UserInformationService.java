package com.todolist.service;

import com.todolist.dao.UserInformationDao;
import com.todolist.entity.UserInformation;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {
    @Autowired
    private UserInformationDao userInformationDao;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    public int updateUserPassword(String rawPassword) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        String encodedPassword = encodePassword(rawPassword);
        userInformation.setUserPassword(encodedPassword);

        return userInformationDao.update(userInformation);
    }

    public int insertUserInformation(UserInformation userInformation) {
        userInformation.setUserPassword(encodePassword(userInformation.getUserPassword()));
        return userInformationDao.insertUserInformation(userInformation);
    }

    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
