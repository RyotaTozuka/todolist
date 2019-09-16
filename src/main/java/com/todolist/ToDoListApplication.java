package com.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ryota Tozuka
 * @version 0.0.1
 *
 * アプリケーション実行クラス
 */
@SpringBootApplication
public class ToDoListApplication {

	/**
	 * プリケーション実行メソッド
	 *
	 * @param args 特に入力なし
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}
}