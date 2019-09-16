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

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Spring Security をはじめ、ユーザ情報の認証系のメソッドを切り出したService
 */
@Service
public class SecureUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInformationDao userInformationDao;

    /**
     * login 認証時に呼ばれるメソッド。
     * login画面から受け取ったユーザ名をもとにログイン認証を行う
     *
     * @link SecureUserDetails
     * @param userName login 画面から受け取ったユーザ名
     * @return ログインを試みているユーザ情報
     * @throws UsernameNotFoundException DB:user_informationにログインユーザ名が存在しないエラー
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInformation userInformation = userInformationDao.selectByUserName(userName);

        if (userInformation == null) {
            throw new UsernameNotFoundException("ユーザ:" + userName + "は存在しません");
        }

        return new SecureUserDetails(userInformation);
    }

    /**
     * SecurityContextHolderからログインしているユーザ情報を取得する
     * 誰もログインしていない状態で呼び出された場合は、権限（userRole）を一般ユーザとした、
     * それ以外の項目がnullであるuserInformationを返す
     *
     * @return userInformation 現在ログインしているユーザ情報
     */
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

    /**
     * メソッドが呼ばれた時点でのログインユーザのパスワードと、入力されたパスワードが等しいかチェックする
     * encoderとしてBCryptPasswordEncoderを使用している
     * @link com.todolist.SecurityConfig#PasswordEncoder
     *
     * @param password 入力されたパスワード（未encode）
     * @return 一致している場合、true
     */
    public boolean checkPasswordValidation(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserInformation userInformation = getUserInformation();

        return passwordEncoder.matches(password, userInformation.getUserPassword());
    }
}
