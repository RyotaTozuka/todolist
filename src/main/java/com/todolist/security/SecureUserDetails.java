package com.todolist.security;

import com.todolist.entity.UserInformation;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecureUserDetails extends User {

    private UserInformation userInformation;

    public SecureUserDetails(UserInformation userInformation) {
        super(
                userInformation.getUserName(),
                userInformation.getUserPassword(),
                AuthorityUtils.createAuthorityList(userInformation.getUserRole()));
    }
}
