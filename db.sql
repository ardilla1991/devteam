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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `email`, `phone`) VALUES
	(1, 'customer 1', 'email', 'phone');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Dumping structure for table devteam.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `start_work` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `qualification_id` int(11) unsigned DEFAULT NULL,
  `role` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_employee_qualification` (`qualification_id`),
  CONSTRAINT `FK_employee_qualification` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.employee: ~0 rows (approximately)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `name`, `login`, `password`, `start_work`, `qualification_id`, `role`) VALUES
	(1, 'manager', 'admin', 'admin', '2017-06-04 21:59:27', 1, 2);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


-- Dumping structure for table devteam.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `description` text,
  `customer_id` int(11) unsigned NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateStart` datetime NOT NULL,
  `dateFinish` datetime NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_project_customer` (`customer_id`),
  CONSTRAINT `FK_project_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.project: ~0 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `title`, `description`, `customer_id`, `dateCreated`, `dateStart`, `dateFinish`, `status`) VALUES
	(1, 'project 1', 'description', 1, '2017-06-04 22:02:23', '2017-06-04 22:02:26', '2017-06-04 22:02:29', 0);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table devteam.project_qualification
CREATE TABLE IF NOT EXISTS `project_qualification` (
  `project_id` int(11) unsigned NOT NULL,
  `qualification_id` int(11) unsigned NOT NULL,
  `count` tinyint(2) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.project_qualification: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_qualification` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_qualification` ENABLE KEYS */;


-- Dumping structure for table devteam.project_work
CREATE TABLE IF NOT EXISTS `project_work` (
  `project_id` int(11) unsigned NOT NULL,
  `work_id` int(11) unsigned NOT NULL,
  `employee_id` int(11) unsigned NOT NULL,
  `hours` int(11) unsigned NOT NULL,
  `description` text,
  KEY `FK_project_work_project` (`project_id`),
  KEY `FK_project_work_work` (`work_id`),
  KEY `FK_project_work_employee` (`employee_id`),
  CONSTRAINT `FK_project_work_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_work_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_work_work` FOREIGN KEY (`work_id`) REFERENCES `work` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.project_work: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_work` ENABLE KEYS */;


-- Dumping structure for table devteam.qualification
CREATE TABLE IF NOT EXISTS `qualification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.qualification: ~0 rows (approximately)
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` (`id`, `title`) VALUES
	(1, 'manager'),
	(2, 'disigner'),
	(3, 'front-end developer'),
	(4, 'junior java developer'),
	(5, 'middle java developer'),
	(6, 'senior java developer');
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;


-- Dumping structure for table devteam.work
CREATE TABLE IF NOT EXISTS `work` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table devteam.work: ~0 rows (approximately)
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
