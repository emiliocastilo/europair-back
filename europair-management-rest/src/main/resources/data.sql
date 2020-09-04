insert into screens values (null, 'screen1', 'screen1_description','2020-07-20','Sistema', null, null);
insert into screens values (null, 'screen2', 'screen2_description','2020-07-20','Sistema', null, null);
insert into screens values (null, 'screen3', 'screen3_description','2020-07-20','Sistema', null, null);

insert into tasks values (null, 'task1', 'task1_description','2020-07-20','Sistema', null, null);
insert into tasks values (null, 'task2', 'task2_description','2020-07-20','Sistema', null, null);
insert into tasks values (null, 'task3', 'task3_description','2020-07-20','Sistema', null, null);

insert into tasks_screens values (1,1);
insert into tasks_screens values (1,2);

insert into tasks_screens values (2,2);
insert into tasks_screens values (2,3);

insert into tasks_screens values (3,3);

insert into roles values (null, 'role1', 'role1_description','2020-07-20','Sistema', null, null);
insert into roles values (null, 'role2', 'role2_description','2020-07-20','Sistema', null, null);
insert into roles values (null, 'role3', 'role3_description','2020-07-20','Sistema', null, null);

insert into roles_tasks values (1,1);

insert into roles_tasks values (2,1);
insert into roles_tasks values (2,2);

insert into roles_tasks values (3,3);

-- pass: jose
-- insert into users values (null, 'jose.mendez', '$2y$10$RmSfGJRyUKh63WIAG6p4dO7ThqkjKWZV2.x1ZJGKBUBvWKpYiQGwW', 'Jose', 'Mendez', 'jose.mendez@prueba.com', 'TWO','2020-07-20','Sistema', null, null);
