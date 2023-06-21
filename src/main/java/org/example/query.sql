create table IF NOT EXISTS addressConsumerHome (
                                                   id bigint PRIMARY KEY AUTO_INCREMENT,
                                                   city varchar(55) NOT NULL,
                                                   street varchar(55) NOT NULL
);

create table IF NOT EXISTS roles (
                                     id int primary key auto_increment,
                                     roles varchar(25) NOT NULL
);

create table IF NOT EXISTS company (
                                       id bigint primary key auto_increment,
                                       name varchar(50) NOT NULL unique
);

create table IF NOT EXISTS subscribe (
                                         id bigint primary key auto_increment,
                                         name varchar(50) not null unique
);


create table if not exists consumer (
                                        id bigint primary key auto_increment,
                                        name varchar(30) not null,
                                        email varchar(40) not null unique,
                                        roles_id int,
                                        address_id bigint,
                                        company_id bigint,
                                        foreign key(address_id) references addressConsumerHome(id) on delete cascade,
                                        foreign key(company_id) references company(id) on delete set null,
                                        foreign key(roles_id) references roles(id) on delete set null
);

create table if not exists subscribe_consumer (
                                                  id bigint primary key auto_increment,
                                                  consumer_id bigint,
                                                  subscribe_id bigint,
                                                  foreign key(consumer_id) references consumer(id),
                                                  foreign key(subscribe_id) references subscribe(id)
);
