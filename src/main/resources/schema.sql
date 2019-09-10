DROP TABLE IF EXISTS todo_list;
DROP TABLE IF EXISTS user_information;

-- ユーザ情報テーブル
CREATE TABLE user_information
(
	user_id integer primary key,
	user_name varchar(20),
	user_password varchar(60)
);

--ToDoリストテーブル
CREATE TABLE todo_list
(
    list_id integer primary key,
	user_id integer references user_information(user_id) not null,
	list_contents varchar(50),
	list_limit varchar(8),
	list_complete_flag boolean not null
);