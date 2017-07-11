-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.13-0ubuntu0.16.04.2 - (Ubuntu)
-- ОС Сервера:                   Linux
-- HeidiSQL Версия:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры для таблица devteam.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.customer: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `user_id`) VALUES
	(1, 'customer 1', 'email', 'phone', 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `start_work` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `qualification_id` int(11) unsigned DEFAULT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_employee_qualification` (`qualification_id`),
  CONSTRAINT `FK_employee_qualification` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.employee: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `name`, `start_work`, `qualification_id`, `user_id`) VALUES
	(1, 'manager', '2017-06-04 21:59:27', 1, 2),
	(2, 'junior developer 1', '2017-06-10 21:30:25', 4, NULL),
	(3, 'disigner', '2017-06-29 21:39:41', 2, NULL),
	(4, 'front-end', '2017-06-29 21:39:51', 3, 3),
	(5, 'middle', '2017-06-29 21:40:00', 5, NULL),
	(6, 'senior', '2017-06-29 21:40:07', 6, NULL),
	(7, 'admin', '2017-07-10 22:59:48', 7, 4);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.order
CREATE TABLE IF NOT EXISTS `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(250) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `specification` char(50) DEFAULT NULL,
  `customer_id` int(11) unsigned NOT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `dateCreated` datetime DEFAULT NULL,
  `dateStart` datetime DEFAULT NULL,
  `dateFinish` datetime DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.order: ~21 rows (приблизительно)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `title`, `description`, `specification`, `customer_id`, `status`, `dateCreated`, `dateStart`, `dateFinish`, `price`) VALUES
	(1, 'order1', 'description', 'file', 1, 0, '2017-06-19 21:59:00', '2017-06-19 21:59:02', '2017-06-30 21:59:03', 0.00),
	(2, 'vkyv', 'gxg', 'xdfx', 1, 0, '2017-06-21 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(3, 'veerv', 'evev', 'evfv', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(48, 'cbvcb', 'xbx', 'bxb', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(49, '6969', '996796', '969', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(50, 'dgf', 'dgdg', 'dfg', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(51, 'BKNKN', 'KN', 'KBNK', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 35.00),
	(58, 'zvz', 'xvxv', 'xv', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(60, 'erg', 'sdg', 'sdg', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(61, 'fgshzhf', '', '', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', 0.00),
	(62, 'sfsfsF', 'sgzhzsdhd', '', 1, 0, '2017-07-01 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(63, 'eGDgDG', 'fbdzg', '', 1, 0, '2017-07-01 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(64, 'SDsd', '', '', 1, 0, '2017-07-01 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(65, 'dgd', '', '', 1, 0, '2017-07-02 00:00:00', '2017-07-01 00:00:00', '2017-07-11 00:00:00', NULL),
	(66, 'sdhshsdh', 'gdjdjdgj', 'Questions.docx', 1, 0, '2017-07-08 00:00:00', '2017-07-14 00:00:00', '2017-07-29 00:00:00', NULL),
	(67, 'asfasa', 'gagag', 'Класс3_задачи.docx', 1, 0, '2017-07-09 00:00:00', '2017-07-29 00:00:00', '2017-07-30 00:00:00', 5.00),
	(68, 'dgzg', 'dzfgdg', 'Questions.docx', 1, 0, '2017-07-09 00:00:00', '2017-07-15 00:00:00', '2017-07-22 00:00:00', NULL),
	(69, 'dgzg', 'dzfgdg', 'Questions.docx', 1, 0, '2017-07-09 00:00:00', '2017-07-15 00:00:00', '2017-07-22 00:00:00', NULL),
	(70, 'dgzg', 'dzfgdg', 'Questions.docx', 1, 0, '2017-07-09 00:00:00', '2017-07-15 00:00:00', '2017-07-22 00:00:00', 2.00),
	(71, 'nfd', 'hdfjxfgj', 'Questions.docx', 1, 0, '2017-07-10 00:00:00', '2017-07-13 00:00:00', '2017-07-29 00:00:00', NULL),
	(72, 'fg', 'mxgmg', 'Questions.docx', 1, 0, '2017-07-10 00:00:00', '2017-10-08 00:00:00', '2017-10-28 00:00:00', 77.00);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.order_qualification
CREATE TABLE IF NOT EXISTS `order_qualification` (
  `order_id` int(11) unsigned NOT NULL,
  `qualification_id` int(11) unsigned NOT NULL,
  `count` tinyint(2) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.order_qualification: ~16 rows (приблизительно)
/*!40000 ALTER TABLE `order_qualification` DISABLE KEYS */;
INSERT INTO `order_qualification` (`order_id`, `qualification_id`, `count`) VALUES
	(51, 4, 1),
	(51, 3, 1),
	(58, 1, 2),
	(60, 1, 2),
	(61, 1, 4),
	(62, 1, 4),
	(63, 4, 5),
	(64, 1, 4),
	(65, 1, 3),
	(66, 3, 1),
	(67, 2, 1),
	(68, 1, 1),
	(69, 1, 1),
	(70, 1, 1),
	(71, 2, 1),
	(72, 2, 1);
/*!40000 ALTER TABLE `order_qualification` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.order_work
CREATE TABLE IF NOT EXISTS `order_work` (
  `order_id` int(11) unsigned NOT NULL,
  `work_id` int(11) unsigned NOT NULL,
  `description` text,
  KEY `FK_project_work_work` (`work_id`),
  KEY `FK_project` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.order_work: ~25 rows (приблизительно)
/*!40000 ALTER TABLE `order_work` DISABLE KEYS */;
INSERT INTO `order_work` (`order_id`, `work_id`, `description`) VALUES
	(48, 3, NULL),
	(48, 2, NULL),
	(49, 0, NULL),
	(49, 3, NULL),
	(50, 4, NULL),
	(50, 1, NULL),
	(51, 1, NULL),
	(51, 2, NULL),
	(58, 2, NULL),
	(60, 4, NULL),
	(60, 1, NULL),
	(61, 1, NULL),
	(62, 4, NULL),
	(62, 1, NULL),
	(63, 4, NULL),
	(63, 1, NULL),
	(64, 4, NULL),
	(64, 1, NULL),
	(66, 4, NULL),
	(67, 4, NULL),
	(68, 4, NULL),
	(69, 4, NULL),
	(70, 4, NULL),
	(71, 4, NULL),
	(72, 4, NULL);
/*!40000 ALTER TABLE `order_work` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `order_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_order` (`order_id`),
  CONSTRAINT `FK_project_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.project: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `title`, `description`, `order_id`) VALUES
	(3, 'проект', 'hdfhzdf', 51),
	(4, 'dFHdfh', 'dzfgjzgjzgj', 67),
	(5, 'sgsdg', 'sdgD', 70),
	(6, 'txjgk', 'gxkxgkxgkx', 72);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.project_employee
CREATE TABLE IF NOT EXISTS `project_employee` (
  `project_id` int(11) unsigned NOT NULL,
  `employee_id` int(11) unsigned NOT NULL,
  `hours` int(11) unsigned NOT NULL,
  KEY `FK_employee_project_employee` (`employee_id`),
  KEY `FK_employee_project_project` (`project_id`),
  CONSTRAINT `FK_employee_project_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employee_project_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.project_employee: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `project_employee` DISABLE KEYS */;
INSERT INTO `project_employee` (`project_id`, `employee_id`, `hours`) VALUES
	(3, 2, 0),
	(3, 4, 4),
	(4, 3, 0),
	(5, 1, 0),
	(6, 3, 0);
/*!40000 ALTER TABLE `project_employee` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.qualification
CREATE TABLE IF NOT EXISTS `qualification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `service` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.qualification: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` (`id`, `title`, `service`) VALUES
	(1, 'manager', 0),
	(2, 'disigner', 0),
	(3, 'front-end developer', 0),
	(4, 'junior java developer', 0),
	(5, 'middle java developer', 0),
	(6, 'senior java developer', 0),
	(7, 'admin', 1);
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(250) NOT NULL,
  `role` enum('DEVELOPER','ADMIN','MANAGER','CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.user: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
	(1, 'c', '$2a$10$zBNfpHh0ZqErueXZCR/XzuZlRQGmOmkSU8ZqakwgMCpCjbo0SG/qW', 'CUSTOMER'),
	(2, 'm', '$2a$10$WrC53OHV6D/3CrjznATTmel2rlJif.j05d0QXxhBlEA6XX/AeHsHa', 'MANAGER'),
	(3, 'fd', '$2a$10$GuRoYflaEACL/fAk5LFgCOfTUv0nwj9EAHNINoCnuAMDoTMKhRxue', 'DEVELOPER'),
	(4, 'admin', '$2a$10$WrC53OHV6D/3CrjznATTmel2rlJif.j05d0QXxhBlEA6XX/AeHsHa', 'ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Дамп структуры для таблица devteam.work
CREATE TABLE IF NOT EXISTS `work` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devteam.work: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` (`id`, `title`, `description`) VALUES
	(1, 'Дизайн', NULL),
	(2, 'Верстка', NULL),
	(3, 'Front end', NULL),
	(4, 'Программирование', NULL);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
