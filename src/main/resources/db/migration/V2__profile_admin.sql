create extension if not exists "uuid-ossp";

insert into profile(name, username, password, status, visible, created_date)
values ('admin',
        'andaqulovxojiakbar@gmail.com', '$2a$10$A9.k9ILyanFIjdyVjwAnvODabzkRSMnUeL0gbn9gYVP0nK2H7VttS', 'ACTIVE',
        true, now());

SELECT setval('profile_id_seq', max(id))
FROM profile;

insert into profile_role(profile_id, roles, created_date)
values (1, 'ROLE_USER', now()),
       (1, 'ROLE_ADMIN', now());