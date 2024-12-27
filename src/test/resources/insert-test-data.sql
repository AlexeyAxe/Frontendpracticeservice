INSERT INTO requisites (id, current_account, bic, correspondent_account, inn, kpp, kbk)
VALUES
    ('2750d0e4-e6e2-4bc6-947a-1acb0e7bdecb', '40702810000000000001', '044525225', '30101810200000000225', '7701234567', '770101001', '18210802020016000130'),
    ('12db1602-7a79-49b4-b5dd-e69386784ea6', '40702810000000000000', '044525224', '30101810200000000226', '7701234568', '770101000', '18210802020016000131'),
    ('152c16a0-1988-47a0-9421-bef0a1e9523e', '40702810000000000002', '044525226', '30101810200000000227', '7701234569', '770101002', '18210802020016000132');

INSERT INTO users (id, first_name, last_name, birthday, snils, login, password, passport_number, requisites_id)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e', 'Петр', 'Петров', '1990-12-12', '11122233345', 'login', 'password', '1234444989', '2750d0e4-e6e2-4bc6-947a-1acb0e7bdecb'),
    ('4856b221-110b-4e7a-a4fc-f308e469c4d9', 'Влад', 'Иванов', '1992-01-12', '11122233350', 'login1', 'password1', '1234444980', '12db1602-7a79-49b4-b5dd-e69386784ea6'),
    ('900a504b-13f5-4996-868d-11b4bc59fd0e', 'Илья', 'Гапеев', '1987-01-04', '11122233351', 'login2', 'password2', '1234444981', '152c16a0-1988-47a0-9421-bef0a1e9523e');

INSERT INTO user_roles (user_id, roles)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','ADMIN'),
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','USER'),
    ('4856b221-110b-4e7a-a4fc-f308e469c4d9','USER'),
    ('900a504b-13f5-4996-868d-11b4bc59fd0e','USER');