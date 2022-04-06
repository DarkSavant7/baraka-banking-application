insert into users (username, password, status)
values ('admin', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'ACTIVE');

insert into roles (name)
values ('ROLE_ADMIN'),
       ('ROLE_CLIENT');

insert into users_roles (user_id, role_id)
values (1, 1);