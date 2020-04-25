insert into users (username, password, enabled)
    values ('admin', '$2a$10$nn4u9BaBkiZctZsYSUpzquOxJBYnEnCAP6JYQ0YJAqBihflRqqydu', true);
insert into authorities (username, authority)
    values ('admin', 'USER');
insert into authorities (username, authority)
    values ('admin', 'ADMIN');

insert into users (username, password, enabled)
    values ('user', '$2a$10$mJb3XZGbkRMsCWgZJ2xRH./dxz2B.Qzsy.oh.mNle4ZEl3UQMup5u', true);
insert into authorities (username, authority)
    values ('user', 'USER');

