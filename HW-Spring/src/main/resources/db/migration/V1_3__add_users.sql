insert into users (id, username, password, enabled)
    values ('22ba3baf-f15d-409a-bc2c-16242db4bd08', 'admin', '$2a$10$nn4u9BaBkiZctZsYSUpzquOxJBYnEnCAP6JYQ0YJAqBihflRqqydu', true);
insert into authorities (user_id, authority)
    values ('22ba3baf-f15d-409a-bc2c-16242db4bd08', 'USER');
insert into authorities (user_id, authority)
    values ('22ba3baf-f15d-409a-bc2c-16242db4bd08', 'ADMIN');
-- password: verysecretpassword

insert into users (id, username, password, enabled)
    values ('bffbd7fd-97f9-4ccd-b427-9f714303cdfa', 'user', '$2a$10$mJb3XZGbkRMsCWgZJ2xRH./dxz2B.Qzsy.oh.mNle4ZEl3UQMup5u', true);
insert into authorities (user_id, authority)
    values ('bffbd7fd-97f9-4ccd-b427-9f714303cdfa', 'USER');
-- password: 12345678