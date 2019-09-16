package com.todolist.service;

import com.todolist.dao.UserInformationDao;
import com.todolist.dto.UserInformationDto;
import com.todolist.entity.UserInformation;
import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInformationService {
    @Autowired
    private UserInformationDao userInformationDao;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    public List<UserInformationForm> selectUserAll() {
        List<UserInformationForm> userInformationForms = new ArrayList<>();
        List<UserInformationDto> userInformationDtos = userInformationDao.selectUserAll();

        for (UserInformationDto userInformationDto : userInformationDtos) {
            UserInformationForm userInformationForm = new UserInformationForm();
            BeanUtils.copyProperties(userInformationDto, userInformationForm);
            userInformationForms.add(userInformationForm);
        }

        return userInformationForms;
    }

    public int updateUserPassword(String rawPassword) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        String encodedPassword = encodePassword(rawPassword);
        userInformation.setUserPassword(encodedPassword);

        return userInformationDao.update(userInformation);
    }

    public int flipUserRoleByUserId(Integer userId) {
        UserInformation userInformation = userInformationDao.selectByUserId(userId);

        if (("ROLE_USER").matches(userInformation.getUserRole())) {
            userInformation.setUserRole("ROLE_ADMIN");
        } else {
            userInformation.setUserRole("ROLE_USER");
        }
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
