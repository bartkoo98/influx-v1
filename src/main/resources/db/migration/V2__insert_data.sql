

INSERT INTO
    categories ( name)
VALUES
    ('Technology'),
    ('Science'),
    ('Business'),
    ('Health'),
    ('Sports'),
    ('Politics'),
    ('Travel'),
    ('Food'),
    ('Fashion'),
    ('Education');


INSERT INTO
    articles (content, title, category_id, created_at)
VALUES
    ('Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'Lorem Ipsum 1', 1, '2023-10-10 08:00:00'),
    ('Sed auctor libero at tortor pharetra, ac volutpat ex fermentum. Sed auctor libero at tortor pharetra, ac volutpat ex fermentum.', 'Lorem Ipsum 2', 3, '2023-10-09 15:30:00'),
    ('Quisque hendrerit nisl sit amet dolor scelerisque, id efficitur orci lacinia. Quisque hendrerit nisl sit amet dolor scelerisque, id efficitur orci lacinia.', 'Lorem Ipsum 3', 3, '2023-10-02 11:30:00'),
    ('Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.', 'Lorem Ipsum 4', 3, '2023-09-09 12:30:00'),
    ('Vestibulum non leo eget elit lacinia sollicitudin. Vestibulum non leo eget elit lacinia sollicitudin.', 'Lorem Ipsum 5', 5, '2023-07-11 10:23:00'),
    ('Maecenas quis metus vitae risus venenatis suscipit. Maecenas quis metus vitae risus venenatis suscipit.', 'Lorem Ipsum 6', 6, '2023-08-09 22:40:00'),
    ('Nulla auctor urna ut ante sollicitudin, in mattis tortor fermentum. Nulla auctor urna ut ante sollicitudin, in mattis tortor fermentum.', 'Lorem Ipsum 7', 7, '2023-09-25 6:35:00'),
    ('Vivamus eu justo id arcu tincidunt cursus in et augue. Vivamus eu justo id arcu tincidunt cursus in et augue.', 'Lorem Ipsum 8', 2, '2023-07-23 10:02:00'),
    ('Fusce fringilla odio sit amet eleifend malesuada. Fusce fringilla odio sit amet eleifend malesuada.', 'Lorem Ipsum 9', 9, '2023-09-16 19:28:00'),
    ('Nunc vel libero sed lorem iaculis volutpat. Nunc vel libero sed lorem iaculis volutpat.', 'Lorem Ipsum 10', 10, '2023-10-02 05:48:00');

INSERT INTO
    roles(name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

INSERT INTO
    users(email, password, username)
VALUES
    ('admin@gmail.com', '$2a$10$lnNBo6rp9/eXSvpWBSSsce7949KpP2.kmhmRlXnpykj91EzNib9h6', 'admin'),
    ('springinflux@gmail.com', '$2a$10$GJc/HTnz285XcvMaBee5/uXM59chVuALz9WCmk1QJsJsPBzBztPce', 'springinflux');

INSERT INTO
    users_roles(user_id, role_id)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO
    subscriptions(user_id)
VALUES
    (2);


INSERT INTO
    comments(body, article_id, user_id)
VALUES
    ('Lorem ipsum dolor sit amet', 1, 2),
    ('Vestibulum non leo eget elit lacinia sollicitudin.', 1, 1),
    ('Lorem ipsum dolor sit amet', 4, 2),
    ('Lorem ipsum dolor sit amet', 5, 2);