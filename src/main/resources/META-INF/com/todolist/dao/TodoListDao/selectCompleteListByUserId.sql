select list_id, contents, due
from todo_list
where user_id = /* userId */1
and is_complete = '1';