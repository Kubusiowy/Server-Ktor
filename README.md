# Konkurs-Server-Ktor
serwer dla systemu konkursowego

baza danych-> 

```sql
CREATE TABLE admin(
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    hash_password VARCHAR(255) NOT NULL
);

CREATE TABLE schools(
    school_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE participants(
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    school_id INT NULL,
    CONSTRAINT fk_participants_chools FOREIGN KEY (school_id) REFERENCES schools(school_id)
);

ALTER TABLE admin
ADD CONSTRAINT uq_login UNIQUE (login);

CREATE TABLE jury(
    jury_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE categories(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE criteria(
    criterion_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(120) NOT NULL,
    max_points INT NOT NULL DEFAULT 10,
    CONSTRAINT fk_cat_crit FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE scores(
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    juror_id INT NOT NULL,
    participant_id INT NOT NULL,
    criterion_id INT NOT NULL,
    points INT NOT NULL,
    CONSTRAINT fk_juror_id FOREIGN KEY (juror_id) REFERENCES jury(jury_id),
    CONSTRAINT fk_participant_id FOREIGN KEY (participant_id) REFERENCES participants(participant_id),
    CONSTRAINT fk_criterion_id FOREIGN KEY (criterion_id) REFERENCES criteria(criterion_id)
);
```
