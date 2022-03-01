create table hibernate_sequence (
    next_val bigint
) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table USER (
                      ID bigint not null,
                      PASSWORD varchar(255) not null,
                      USERNAME varchar(255) not null,
                      primary key (ID)
) engine=InnoDB;
create table USER_ROLE (
                           USER_ID bigint not null,
                           roles varchar(255)
) engine=InnoDB;
alter table USER
    add constraint UK_lb5yrvw2c22im784wwrpwuq06 unique (USERNAME);
alter table USER_ROLE
    add constraint FKa8x5mvctia7u43u2mm3hyy5bm
        foreign key (USER_ID)
            references USER (ID);
insert into USER values
(1,'user', 'user'),
(2,'admin', 'admin'),
(3,'moder', 'moder');
insert into USER_ROLE values
(1,'USER'),
(2,'ADMIN'),
(3,'MODERATOR');

