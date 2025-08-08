
    create table site_user (
        id bigint not null auto_increment,
        email varchar(255),
        password varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table if exists site_user 
       add constraint UK8vlkw482t3gpnebxcm03ywk9p unique (email);

    alter table if exists site_user 
       add constraint UKjerlw3g2urnh55wcrm2b5kqnj unique (username);
