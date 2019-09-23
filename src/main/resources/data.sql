insert into user_information (user_name, user_password, user_role) values ('Tozuka', '$2a$10$exc.1/uNj81xqK3AdRCj/OyVRqeErJOa0iD5v3DqvJ0S6FMlDm0my', 'ROLE_ADMIN');-- パスワード：pass
insert into user_information (user_name, user_password, user_role) values ('Tanaka', '$2a$10$JZtpQOXNbZo5Uti3NkQ2decFigtDAzPzrTnIMTngWO6zD6mBQetae', 'ROLE_USER');-- パスワード：password

insert into todo_list (user_id, contents, due, is_complete) values (1,'〇〇を買う','2019-12-01','0');
insert into todo_list (user_id, contents, due, is_complete) values (1,'△△の準備','2019-12-05','0');
insert into todo_list (user_id, contents, due, is_complete) values (1,'□□をやる','2019-10-02','1');
insert into todo_list (user_id, contents, due, is_complete) values (2,'□□に☆を返す','2019-11-06','0');