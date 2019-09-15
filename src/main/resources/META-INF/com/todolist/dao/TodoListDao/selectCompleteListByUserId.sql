select list_id, list_contents, list_limit
from todo_list
where user_id = /* userId */1
and list_complete_flag = '1';