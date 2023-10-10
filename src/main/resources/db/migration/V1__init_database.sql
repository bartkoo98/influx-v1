
CREATE TABLE if not exists categories
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists  articles
(
    id INT generated always as identity primary key,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE if not exists roles
(
    id INT generated always as identity primary key,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE if not exists users
(
    id INT generated always as identity primary key,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists users_roles
(
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE if not exists subscriptions
(
    id INT generated always as identity primary key,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE if not exists comments
(
    id INT generated always as identity primary key,
    body TEXT NOT NULL,
    article_id INT NOT NULL,
    user_id INT,
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
