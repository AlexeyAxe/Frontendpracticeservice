INSERT INTO users (id, first_name, last_name, birthday, inn, snils, login, password, passport_number)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e', 'Петр', 'Петров', '2024-12-12', '111222333456', '11122233345', 'login', 'password', '1234444989');

INSERT INTO user_roles (user_id, roles)
VALUES
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','ADMIN'),
    ('b5ca20bc-841e-4e58-ad53-bbb165e2329e','USER');