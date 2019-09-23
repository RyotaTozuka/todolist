package com.todolist.security;

import com.todolist.entity.UserInformation;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Spring Security の認証時に呼び出される
 * login form から受け取ったusernameをもとに、ユーザの認証情報を抽出する
 * @link SecureUserDetailsService#loadUserByUsername(String userName)
 */
class SecureUserDetails extends User {

    SecureUserDetails(UserInformation userInformation) {
        super(
                userInformation.getUserName(),
                userInformation.getUserPassword(),
                AuthorityUtils.createAuthorityList(userInformation.getUserRole()));
    }
}
