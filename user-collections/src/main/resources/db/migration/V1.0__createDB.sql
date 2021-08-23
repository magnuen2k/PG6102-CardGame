drop table if exists card_copy CASCADE;
drop table if exists user_data CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table card_copy (id bigint not null, card_id varchar(255), number_of_copies integer not null check (number_of_copies>=0), user_user_id varchar(255) not null, primary key (id));
create table user_data (user_id varchar(255) not null, card_packs integer not null check (card_packs>=0), coins integer not null check (coins>=0), primary key (user_id));
alter table card_copy add constraint FKb5or6ruraxeeyoov9071kykmw foreign key (user_user_id) references user_data;