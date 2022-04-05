create table users(
    id bigserial not null unique primary key,
    email varchar(50) not null unique,
    password varchar(150)
);

create table roles(
    id serial not null unique primary key,
    name varchar(30) not null unique
);

create table users_roles(
    user_id bigint references users(id) primary key,
    role_id int references roles(id)
);

insert into users(email, password) VALUES
('a@gg.ru', '$2a$10$lj7J5uuTThjy.6WpUNGJuuha0/IuoqkY4Y7HZyGwCbZ10Xi10yjWu'),
('b@gg.ru', 'test2'),
('c@gg.ru', 'test3'),
('d@gg.ru', 'test4'),
('e@gg.ru', 'test5');

insert into roles(name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

insert into users_roles (user_id, role_id) VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 2),
(5, 1);
