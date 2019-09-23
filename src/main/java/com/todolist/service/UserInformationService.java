package com.todolist.service;

import com.todolist.dao.UserInformationDao;
import com.todolist.dto.UserInformationDto;
import com.todolist.entity.UserInformation;
import com.todolist.form.UserInformationForm;
import com.todolist.security.SecureUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * UserInformation の情報を扱うServiceクラス
 */
@Service
public class UserInformationService {
    @Autowired
    private UserInformationDao userInformationDao;

    @Autowired
    private SecureUserDetailsService secureUserDetailsService;

    /**
     * ユーザの全件抽出
     *
     * @return List<UserInformationForm> の型で抽出、0件の場合は size=0 のListを返す
     */
    public List<UserInformationForm> selectUserAll() {
        List<UserInformationDto> userInformationDtos = userInformationDao.selectUserAll();
        List<UserInformationForm> userInformationForms = new ArrayList<>();

        //DtoクラスからFormクラスにデータを移し替える
        for (UserInformationDto userInformationDto : userInformationDtos) {
            UserInformationForm userInformationForm = new UserInformationForm();
            BeanUtils.copyProperties(userInformationDto, userInformationForm);
            userInformationForms.add(userInformationForm);
        }

        return userInformationForms;
    }

    /**
     * ログイン中のユーザのパスワードの更新
     *
     * @param rawPassword パスワード、encodeはメソッド内で実施
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
    public int updateUserPassword(String rawPassword) {
        UserInformation userInformation = secureUserDetailsService.getUserInformation();
        //encodeはクラス内部のメソッドにて実施
        String encodedPassword = encodePassword(rawPassword);
        userInformation.setUserPassword(encodedPassword);

        return userInformationDao.update(userInformation);
    }

    /**
     * ユーザの権限情報を変更
     * ※権限の種類は、"ROLE_USER","ROLE_ADMIN"の二種類であるため
     * "ROLE_USER"でなければ"ROLE_ADMIN"であると考える、という実装をしている。
     *
     * @param userId 選択されたユーザId
     * @return 更新完了件数（1ならば正常、0ならば異常）
     */
     public int flipUserRoleByUserId(Integer userId) {
        UserInformation userInformation = userInformationDao.selectByUserId(userId);

        //権限情報の変更
        if (("ROLE_USER").matches(userInformation.getUserRole())) {
            userInformation.setUserRole("ROLE_ADMIN");
        } else {
            userInformation.setUserRole("ROLE_USER");
        }
        return userInformationDao.update(userInformation);
    }

    /**
     * 入力されたユーザ情報の挿入をする
     * 入力のパスワードはencode前のもの、
     * encodeは本メソッド内で実施する
     *
     * @param userInformation 入力されたuserInformation情報
     * @return 挿入完了件数（1ならば正常、0ならば異常）
     */
    public int insertUserInformation(UserInformation userInformation) {
        //パスワードは平文のままなので、エンコードする。
        userInformation.setUserPassword(encodePassword(userInformation.getUserPassword()));
        return userInformationDao.insertUserInformation(userInformation);
    }

    /**
     * 入力されたuserIdに紐づくユーザ情報を削除する。
     *
     * @param userId 入力されたユーザId
     * @return 削除完了件数（1ならば正常、0ならば異常）
     */
    public int deleteUserByUserId(Integer userId) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(userId);

        return userInformationDao.delete(userInformation);
    }

    /**
     * 入力されたユーザ名が、すでにDBに登録されているかチェックする
     *
     * @param userName ユーザ名
     * @return true：入力されたユーザ名はDBにまだ登録されていない、false：すでに登録あり
     */
    public boolean isUniqueUserName(String userName) {
        List<UserInformationDto> userInformationDtos = userInformationDao.selectUserAll();

        for ( UserInformationDto userInformationDto : userInformationDtos) {
            if (userName.matches(userInformationDto.getUserName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * パスワードをencodeする
     * encoderはBCryptPasswordEncoderを使用
     *
     * @link com.todolist.SecurityConfig#PasswordEncoder
     * @param password encode前のパスワード
     * @return encode後のパスワード
     */
    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
