<<<<<<< HEAD
# Ktor Serwer

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                               | Description                                                                        |
| --------------------------------------------------------------------|------------------------------------------------------------------------------------ |
| [CORS](https://start.ktor.io/p/cors)                               | Enables Cross-Origin Resource Sharing (CORS)                                       |
| [Routing](https://start.ktor.io/p/routing)                         | Provides a structured routing DSL                                                  |
| [Authentication](https://start.ktor.io/p/auth)                     | Provides extension point for handling the Authorization header                     |
| [Authentication JWT](https://start.ktor.io/p/auth-jwt)             | Handles JSON Web Token (JWT) bearer authentication scheme                          |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation) | Provides automatic content conversion according to Content-Type and Accept headers |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

=======
![linia](https://www.gify.net/data/media/562/linia-ruchomy-obrazek-0184.gif)
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
>>>>>>> 82eddc6f795827b63c6319db95194eea1b11c916
