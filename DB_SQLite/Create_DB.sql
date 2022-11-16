create table user
(
    user_id integer not null primary key,
    username TEXT,
    birthday TEXT,
    email TEXT
);

create table group_list
(
    id INTEGER not null primary key,
    group_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY(user_id) REFERENCES user(user_id)
);

create table message
(
    message_id INTEGER not null primary key,
    group_id INTEGER,
    user_id INTEGER,
    message TEXT,
    FOREIGN KEY(user_id) REFERENCES user(user_id),
    FOREIGN KEY(group_id) REFERENCES group_list(group_id)
);

create table alarm
(
    alarm_id integer not null primary key,
    alarm_user INTEGER,
    alarm_group INTEGER,
    time TEXT,
    FOREIGN KEY(alarm_user) REFERENCES user(user_id),
    FOREIGN KEY(alarm_group) REFERENCES group_list(group_id)
);

create table active_alarm
(
    id integer not null primary key,
    user_id INTEGER,
    alarm_id INTEGER,
    FOREIGN KEY(user_id) REFERENCES user(user_id),
    FOREIGN KEY(alarm_id) REFERENCES alarm(alarm_id)
);

