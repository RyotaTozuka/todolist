package com.todolist.security;

import com.todolist.dao.UserInformationDao;
import com.todolist.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public UserInformation getUserInformation() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userInformationDao.selectByUserName(username);
        } else {
            UserInformation userInformation = new UserInformation();
            userInformation.setUserRole("ROLE_USER");
            return userInformation;
        }
    }

    public boolean checkPasswordValidation(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserInformation userInformation = getUserInformation();

        return passwordEncoder.matches(password, userInformation.getUserPassword());
    }

    public String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
