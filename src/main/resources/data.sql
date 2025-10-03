create table IF NOT EXISTS users (
                       id                    UUID,
                       username              varchar(30) not null,
                       passport              varchar(30) not null unique,
                       password              varchar(80) not null,
                       email                 varchar(50) not null unique,
                       primary key (id)
);

create table IF NOT EXISTS roles (
                       id                    serial,
                       role                  varchar(50) not null,
                       primary key (id)
);

CREATE TABLE IF NOT EXISTS users_roles (
                             user_id               UUID not null,
                             role_id               int not null,
                             primary key (user_id, role_id),
                             foreign key (user_id) references users (id),
                             foreign key (role_id) references roles (id)
);

insert into roles (id, role)
SELECT'1', 'ROLE_USER'
    WHERE NOT EXISTS (
    SELECT 1
    FROM roles
    WHERE role = 'ROLE_USER' AND id = '1'
);

insert into roles (id, role)
SELECT '2','ROLE_ADMIN'
    WHERE NOT EXISTS (
    SELECT 1
    FROM roles
    WHERE role = 'ROLE_ADMIN' AND id = '2'
);

INSERT INTO users (id, username, passport, password, email)
SELECT 'e35a3008-462d-45b6-9245-47c86e672111', 'admin2',
       '0000000000', '$2a$10$6zKo9mTACOLoCl9R9O6JEOOosa7BF3ICAeEUm7EKYyfGVXHbvKUBa',
       'admin2@example.com'
WHERE NOT EXISTS(
    SELECT 1
    FROM users
    WHERE passport = '0000000000' OR id = 'e35a3008-462d-45b6-9245-47c86e672111' OR email = 'admin2@example.com'
);