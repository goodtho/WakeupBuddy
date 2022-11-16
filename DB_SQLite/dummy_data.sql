
INSERT INTO user (user_id, username, birthday, email)
VALUES
   (1, 'user1', '01.01.1970', 'user1@email.com'),
   (2, 'user2', '01.01.1970', 'user2@email.com'),
   (3, 'user3', '01.01.1970', 'user3@email.com');

INSERT INTO group_list (id, group_id, user_id)
VALUES
   (1, 1, 1),
   (2, 1, 3);

INSERT INTO message (message_id, group_id, user_id, message)
VALUES
   (1, 1, 1, 'Hello World from user 1 to group 1'),
   (2, 1, 3, 'from user 3 to group 1');

INSERT INTO alarm (alarm_id, alarm_user, alarm_group, time)
VALUES
    (1, 1, 1, '07:00'),
    (2, 3, 1, '07:15');

INSERT INTO active_alarm (id, user_id, alarm_id)
VALUES
    (1, 1, 1);