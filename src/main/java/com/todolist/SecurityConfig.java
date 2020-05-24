package com.todolist;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * Spring Secure の Configクラス
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 認証を無視するリソースの定義
     *
     * @param web ウェブ
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    /**
     * リソースごとの参照権限
     * login、logout時の設定
     *
     * @param http http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //参照制限なし、login、ユーザ新規登録時の画面リソース
                .antMatchers("/login","/header/header","/user/createUser","/user/createUser/create")
                .permitAll()
                //admin配下は、権限："ROLE_ADMIN"のみが参照可能
                .antMatchers("/admin/**").hasRole("ADMIN")
                //その他画面は"ROLE_USER"以上の参照権限が必要
                .anyRequest()
                .authenticated()
                .and()
                //loginページの設定
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/login/todoList", true)
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                //logout時の設定
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }

    //パスワードはBCryptPasswordEncoderでencode
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}