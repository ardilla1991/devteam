-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.12-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for devteam
CREATE DATABASE IF NOT EXISTS `devteam` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `devteam`;


-- Dumping structure for table devteam.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `user_id`) VALUES
	(1, 'customer 1', 'email', 'phone', 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Dumping structure for table devteam.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `start_work` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `qualification_id` int(11) unsigned DEFAULT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_employee_qualification` (`qualification_id`),
  CONSTRAINT `FK_employee_qualification` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.employee: ~2 rows (approximately)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `name`, `start_work`, `qualification_id`, `user_id`) VALUES
	(1, 'manager', '2017-06-04 21:59:27', 1, 2),
	(2, 'developer 1', '2017-06-10 21:30:25', 4, NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


-- Dumping structure for table devteam.employee_project
CREATE TABLE IF NOT EXISTS `employee_project` (
  `employee_id` int(11) unsigned NOT NULL,
  `project_id` int(11) unsigned NOT NULL,
  `hours` int(11) unsigned NOT NULL,
  KEY `FK_employee_project_employee` (`employee_id`),
  KEY `FK_employee_project_project` (`project_id`),
  CONSTRAINT `FK_employee_project_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employee_project_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.employee_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `employee_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_project` ENABLE KEYS */;


-- Dumping structure for table devteam.order
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
  `price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.order: ~15 rows (approximately)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `title`, `description`, `specification`, `customer_id`, `status`, `dateCreated`, `dateStart`, `dateFinish`, `price`) VALUES
	(1, 'order1', 'description', 'file', 1, 0, '2017-06-19 21:59:00', '2017-06-19 21:59:02', '2017-06-30 21:59:03', NULL),
	(2, 'vkyv', 'gxg', 'xdfx', 1, 0, '2017-06-21 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(3, 'veerv', 'evev', 'evfv', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(48, 'cbvcb', 'xbx', 'bxb', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(49, '6969', '996796', '969', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(50, 'dgf', 'dgdg', 'dfg', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(51, 'BKNKN', 'KN', 'KBNK', 1, 0, '2017-06-22 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(52, 'sfs', 'DFSF', 'sdfs', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(53, 'sfs', 'DFSF', 'sdfs', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(54, 'asca', 'saca', 'csac', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(55, 'asca', 'saca', 'csac', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(56, 'sdsd', 'sdfsf', 'sf', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(57, 'dgdzg', 'dzfgzdg', 'zdfg', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(58, 'zvz', 'xvxv', 'xv', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(59, 'egreg', 'arh', 'harh', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(60, 'erg', 'sdg', 'sdg', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL),
	(61, 'fgshzhf', '', '', 1, 0, '2017-06-25 00:00:00', '2016-12-31 00:00:00', '2016-12-31 00:00:00', NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;


-- Dumping structure for table devteam.order_qualification
CREATE TABLE IF NOT EXISTS `order_qualification` (
  `order_id` int(11) unsigned NOT NULL,
  `qualification_id` int(11) unsigned NOT NULL,
  `count` tinyint(2) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.order_qualification: ~4 rows (approximately)
/*!40000 ALTER TABLE `order_qualification` DISABLE KEYS */;
INSERT INTO `order_qualification` (`order_id`, `qualification_id`, `count`) VALUES
	(51, 4, 1),
	(51, 3, 1),
	(58, 1, 2),
	(60, 1, 2),
	(61, 1, 4);
/*!40000 ALTER TABLE `order_qualification` ENABLE KEYS */;


-- Dumping structure for table devteam.order_work
CREATE TABLE IF NOT EXISTS `order_work` (
  `order_id` int(11) unsigned NOT NULL,
  `work_id` int(11) unsigned NOT NULL,
  `description` text,
  KEY `FK_project_work_work` (`work_id`),
  KEY `FK_project` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.order_work: ~21 rows (approximately)
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
	(52, 2, NULL),
	(53, 2, NULL),
	(54, 1, NULL),
	(54, 2, NULL),
	(55, 1, NULL),
	(55, 2, NULL),
	(56, 1, NULL),
	(56, 2, NULL),
	(57, 4, NULL),
	(57, 1, NULL),
	(58, 2, NULL),
	(59, 4, NULL),
	(60, 4, NULL),
	(60, 1, NULL),
	(61, 1, NULL);
/*!40000 ALTER TABLE `order_work` ENABLE KEYS */;


-- Dumping structure for table devteam.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `description` text,
  `order_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_order` (`order_id`),
  CONSTRAINT `FK_project_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.project: ~3 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `title`, `description`, `order_id`) VALUES
	(1, 'project 1', 'description', 1),
	(2, 'project 2', 'description 2', 1),
	(3, 'project 3', 'description 3', 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table devteam.qualification
CREATE TABLE IF NOT EXISTS `qualification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.qualification: ~6 rows (approximately)
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` (`id`, `title`) VALUES
	(1, 'manager'),
	(2, 'disigner'),
	(3, 'front-end developer'),
	(4, 'junior java developer'),
	(5, 'middle java developer'),
	(6, 'senior java developer');
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;


-- Dumping structure for table devteam.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('DEVELOPER','ADMINISTRATOR','MANAGER','CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.user: ~1 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
	(1, 'admin', 'admin', 'CUSTOMER'),
	(2, 'm', '1', 'MANAGER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table devteam.work
CREATE TABLE IF NOT EXISTS `work` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.work: ~4 rows (approximately)
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` (`id`, `title`, `description`, `price`) VALUES
	(1, 'Дизайн', NULL, 20),
	(2, 'Верстка', NULL, 20),
	(3, 'Front end', NULL, 50),
	(4, 'Программирование', NULL, 50);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
