INSERT INTO requisites (id, current_account, bic, correspondent_account, inn, kpp, kbk)
VALUES
    ('35d045b8262448699193e44cb81d920c', '40702810000000000001', '044525225', '30101810200000000225', '7701234567', '770101001', '18210802020016000130');

INSERT INTO users (id, first_name, last_name, birthday, snils, login, password, passport_number, requisites_id)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e', 'Петр', 'Петров', '2024-12-12', '11122233345', 'login', 'password', '1234444989', '35d045b8262448699193e44cb81d920c');

INSERT INTO user_roles (user_id, roles)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','ADMIN'),
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','USER');