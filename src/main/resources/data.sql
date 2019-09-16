insert into user_information values (1, 'Tozuka', '$2a$10$exc.1/uNj81xqK3AdRCj/OyVRqeErJOa0iD5v3DqvJ0S6FMlDm0my', 'ROLE_ADMIN');-- パスワード：pass
insert into user_information values (2, 'Tanaka', '$2a$10$JZtpQOXNbZo5Uti3NkQ2decFigtDAzPzrTnIMTngWO6zD6mBQetae', 'ROLE_USER');-- パスワード：password

insert into todo_list (user_id, list_contents, list_limit, list_complete_flag) values (1,'〇〇を買う','2019-12-01','0');
insert into todo_list (user_id, list_contents, list_limit, list_complete_flag) values (1,'△△の準備','2019-12-05','0');
insert into todo_list (user_id, list_contents, list_limit, list_complete_flag) values (1,'□□をやる','2019-10-02','1');
insert into todo_list (user_id, list_contents, list_limit, list_complete_flag) values (2,'□□に☆を返す','2019-11-06','0');