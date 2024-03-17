INSERT INTO t_role (role_name)
VALUES
    ('USER'),
    ('ADMIN');

INSERT INTO t_user (email, password)
VALUES
    ('otheruser@mail.com','$2a$10$85skklE47.X97E3W9.UGoOQZ9T21i6ENL3yhjV.wT85X.2hBXV/uu');

INSERT INTO user_role(user_id, role_id)
VALUES
    (1,1),
    (1,2);

INSERT INTO account (user_id, first_name, last_name, date_of_birth, country, gender, balance)
VALUES
    (1, 'Nazar', 'Hajdych', '1999-04-12', 'Ukraine', 'MALE', 1234.4);

INSERT INTO payment (account_id, amount)
VALUES
    (1, 123.5);