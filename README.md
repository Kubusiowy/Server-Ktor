[konkurs.sql](https://github.com/user-attachments/files/21978615/konkurs.sql)![linia](https://www.gify.net/data/media/562/linia-ruchomy-obrazek-0184.gif)
# Konkurs-Server-Ktor
Backend systemu oceniania (ktor + MySql)

## Struktura bazy (MYSQL)

[Uploading konkurs-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 26, 2025 at 03:00 AM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `konkurs`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `login` varchar(255) NOT NULL,
  `hash_password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `login`, `hash_password`) VALUES
(1, 'admin', 'hash123');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, 'Obsługa gościa'),
(2, 'Housekeeping');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `criteria`
--

CREATE TABLE `criteria` (
  `criterion_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(120) NOT NULL,
  `max_points` int(11) NOT NULL DEFAULT 10
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `criteria`
--

INSERT INTO `criteria` (`criterion_id`, `category_id`, `name`, `max_points`) VALUES
(1, 1, 'Powitanie i prezencja', 10),
(2, 1, 'Komunikacja i język', 20),
(3, 2, 'Czystość pokoju', 15),
(4, 2, 'Organizacja pracy', 15);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `jury`
--

CREATE TABLE `jury` (
  `jury_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jury`
--

INSERT INTO `jury` (`jury_id`, `first_name`, `last_name`) VALUES
(1, 'Anna', 'Kowalska'),
(2, 'Jan', 'Nowak');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `participants`
--

CREATE TABLE `participants` (
  `participant_id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `school_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` (`participant_id`, `first_name`, `last_name`, `school_id`) VALUES
(1, 'Ola', 'Zielińska', 1),
(2, 'Michał', 'Wiśniewski', 2),
(3, 'Kinga', 'Mazur', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `schools`
--

CREATE TABLE `schools` (
  `school_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schools`
--

INSERT INTO `schools` (`school_id`, `name`, `city`) VALUES
(1, 'ZS Hotelarskie', 'Warszawa'),
(2, 'Technikum Gastronomiczne', 'Kraków'),
(3, 'ZS nr 3', 'Gdańsk');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `scores`
--

CREATE TABLE `scores` (
  `score_id` int(11) NOT NULL,
  `juror_id` int(11) NOT NULL,
  `participant_id` int(11) NOT NULL,
  `criterion_id` int(11) NOT NULL,
  `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `scores`
--

INSERT INTO `scores` (`score_id`, `juror_id`, `participant_id`, `criterion_id`, `points`) VALUES
(1, 1, 1, 1, 9),
(2, 1, 1, 2, 18),
(3, 2, 2, 1, 7),
(4, 2, 2, 3, 12);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `uq_login` (`login`);

--
-- Indeksy dla tabeli `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indeksy dla tabeli `criteria`
--
ALTER TABLE `criteria`
  ADD PRIMARY KEY (`criterion_id`),
  ADD KEY `fk_cat_crit` (`category_id`);

--
-- Indeksy dla tabeli `jury`
--
ALTER TABLE `jury`
  ADD PRIMARY KEY (`jury_id`);

--
-- Indeksy dla tabeli `participants`
--
ALTER TABLE `participants`
  ADD PRIMARY KEY (`participant_id`),
  ADD KEY `fk_participants_chools` (`school_id`);

--
-- Indeksy dla tabeli `schools`
--
ALTER TABLE `schools`
  ADD PRIMARY KEY (`school_id`);

--
-- Indeksy dla tabeli `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`score_id`),
  ADD KEY `fk_juror_id` (`juror_id`),
  ADD KEY `fk_participant_id` (`participant_id`),
  ADD KEY `fk_criterion_id` (`criterion_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `criteria`
--
ALTER TABLE `criteria`
  MODIFY `criterion_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jury`
--
ALTER TABLE `jury`
  MODIFY `jury_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `participants`
--
ALTER TABLE `participants`
  MODIFY `participant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `schools`
--
ALTER TABLE `schools`
  MODIFY `school_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `scores`
--
ALTER TABLE `scores`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `criteria`
--
ALTER TABLE `criteria`
  ADD CONSTRAINT `fk_cat_crit` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Constraints for table `participants`
--
ALTER TABLE `participants`
  ADD CONSTRAINT `fk_participants_chools` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`);

--
-- Constraints for table `scores`
--
ALTER TABLE `scores`
  ADD CONSTRAINT `fk_criterion_id` FOREIGN KEY (`criterion_id`) REFERENCES `criteria` (`criterion_id`),
  ADD CONSTRAINT `fk_juror_id` FOREIGN KEY (`juror_id`) REFERENCES `jury` (`jury_id`),
  ADD CONSTRAINT `fk_participant_id` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`participant_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
.sql…]()

![linia](https://www.gify.net/data/media/562/linia-ruchomy-obrazek-0184.gif)
