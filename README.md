[konkurs.sql](https://github.com/user-attachments/files/21978615/konkurs.sql)![linia](https://www.gify.net/data/media/562/linia-ruchomy-obrazek-0184.gif)
# Konkurs-Server-Ktor
Backend systemu oceniania (ktor + MySql)

## Struktura bazy (MYSQL)

```sql
-- Schema konkurs database
CREATE DATABASE IF NOT EXISTS konkurs;
USE konkurs;

-- Admin
CREATE TABLE admin(
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    hash_password VARCHAR(255) NOT NULL
);

ALTER TABLE admin
ADD CONSTRAINT uq_login UNIQUE (login);

-- Schools
CREATE TABLE schools(
    school_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

-- Participants
CREATE TABLE participants(
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    school_id INT NULL,
    CONSTRAINT fk_participants_chools FOREIGN KEY (school_id) REFERENCES schools(school_id)
);

-- Jury
CREATE TABLE jury(
    jury_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

-- Categories
CREATE TABLE categories(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Criteria
CREATE TABLE criteria(
    criterion_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(120) NOT NULL,
    max_points INT NOT NULL DEFAULT 10,
    CONSTRAINT fk_cat_crit FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Scores
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

![linia](https://www.gify.net/data/media/562/linia-ruchomy-obrazek-0184.gif)
