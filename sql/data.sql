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
-- Dumping data for table devteam.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `user_id`) VALUES
	(1, 'Васильев Василий Иванович', 'email@email.com', '+375172222222', 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping data for table devteam.employee: ~13 rows (approximately)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `name`, `start_work`, `qualification_id`, `user_id`) VALUES
	(1, 'Иванов Иван', '2017-06-04 15:59:27', 1, 2),
	(2, 'Петров Иван', '2017-06-10 15:30:25', 4, 5),
	(3, 'Петров Петр', '2017-06-29 15:39:41', 2, 6),
	(4, 'Иванов Петр', '2017-06-29 15:39:51', 3, 3),
	(5, 'Сидоров Петр', '2017-06-29 15:40:00', 5, 7),
	(6, 'Сидоров Иван', '2017-06-29 15:40:07', 6, 8),
	(7, 'Просто Катя', '2017-07-10 16:59:48', 7, 4),
	(17, 'Федоров Иван', '2017-07-13 06:24:08', 1, 9),
	(18, 'Федоров Петр', '2017-07-13 06:24:33', 4, 10),
	(19, 'Иванов Федор', '2017-07-13 06:24:51', 2, NULL),
	(20, 'Петров Федор', '2017-07-13 06:25:16', 3, NULL),
	(21, 'Сидоров Федор', '2017-07-13 06:25:53', 5, NULL),
	(22, 'Сидоров Александр', '2017-07-13 06:27:45', 6, NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

-- Dumping data for table devteam.order: ~10 rows (approximately)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `title`, `description`, `specification`, `customer_id`, `dateCreated`, `dateStart`, `dateFinish`, `dateProcessing`, `price`) VALUES
	(76, 'Проект 1', 'site1.com', 'Т31.odt', 1, '2017-07-13 00:00:00', '2017-11-30 00:00:00', '2018-04-18 00:00:00', NULL, NULL),
	(77, 'Проект 2', 'site2.com', 'Т32.odt', 1, '2017-07-13 00:00:00', '2017-07-20 00:00:00', '2017-09-29 00:00:00', NULL, NULL),
	(78, 'Сайт 1', 'project.com', 'Т34.odt', 1, '2017-07-13 00:00:00', '2017-08-19 00:00:00', '2017-08-30 00:00:00', NULL, NULL),
	(79, 'Сайт 2', 'site.com', 'Т35.odt', 1, '2017-07-13 00:00:00', '2017-08-12 00:00:00', '2017-08-24 00:00:00', NULL, NULL),
	(80, 'Сайт 3', 'siteN.com', 'Т36.odt', 1, '2017-07-13 00:00:00', '2018-10-21 00:00:04', '2018-12-23 00:00:00', NULL, NULL),
	(81, 'Сайт 4', 'site12.com', 'Т37.odt', 1, '2017-07-13 00:00:00', '2017-12-02 00:00:00', '2018-05-23 00:00:00', NULL, NULL),
	(82, 'Сайт 5', 'site5.com', 'Т38.odt', 1, '2017-07-13 00:00:00', '2017-12-16 00:00:00', '2018-04-26 00:00:00', NULL, NULL),
	(83, 'Сайт 6', 'site4.com', 'Т39.odt', 1, '2017-07-13 00:00:00', '2017-07-31 00:00:00', '2017-08-26 00:00:00', NULL, NULL),
	(84, 'Проект 3', 'site3.com', 'Т33.odt', 1, '2017-07-14 00:00:00', '2017-07-14 00:00:00', '2017-07-28 00:00:00', NULL, NULL),
	(86, 'Equipment', 'Equipment', 'Equipment task.docx', 1, '2017-07-30 00:00:00', '2017-10-26 00:00:00', '2017-11-24 00:00:00', NULL, NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Dumping data for table devteam.order_qualification: ~22 rows (approximately)
/*!40000 ALTER TABLE `order_qualification` DISABLE KEYS */;
INSERT INTO `order_qualification` (`order_id`, `qualification_id`, `count`) VALUES
	(76, 1, 1),
	(76, 3, 1),
	(76, 2, 1),
	(76, 5, 1),
	(77, 6, 1),
	(77, 2, 1),
	(77, 4, 1),
	(78, 2, 1),
	(78, 5, 1),
	(79, 6, 1),
	(80, 2, 1),
	(80, 5, 1),
	(81, 6, 1),
	(81, 3, 1),
	(81, 2, 1),
	(82, 6, 1),
	(82, 3, 1),
	(82, 2, 1),
	(83, 6, 1),
	(83, 2, 1),
	(84, 2, 1),
	(86, 6, 1);
/*!40000 ALTER TABLE `order_qualification` ENABLE KEYS */;

-- Dumping data for table devteam.order_work: ~28 rows (approximately)
/*!40000 ALTER TABLE `order_work` DISABLE KEYS */;
INSERT INTO `order_work` (`order_id`, `work_id`, `description`) VALUES
	(76, 4, NULL),
	(76, 1, NULL),
	(76, 2, NULL),
	(76, 3, NULL),
	(77, 4, NULL),
	(77, 1, NULL),
	(77, 2, NULL),
	(77, 3, NULL),
	(78, 4, NULL),
	(78, 1, NULL),
	(78, 2, NULL),
	(79, 4, NULL),
	(79, 2, NULL),
	(80, 4, NULL),
	(80, 1, NULL),
	(80, 2, NULL),
	(81, 4, NULL),
	(81, 1, NULL),
	(81, 2, NULL),
	(82, 4, NULL),
	(82, 1, NULL),
	(82, 2, NULL),
	(82, 3, NULL),
	(83, 4, NULL),
	(83, 1, NULL),
	(83, 2, NULL),
	(84, 4, NULL),
	(86, 4, NULL);
/*!40000 ALTER TABLE `order_work` ENABLE KEYS */;

-- Dumping data for table devteam.project: ~3 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `title`, `description`, `dateCreated`, `order_id`) VALUES
	(10, 'Проект 1', 'site1.com', '2017-07-14 12:32:21', 76),
	(11, 'Проект 2', 'site2.com', '2017-07-14 12:32:21', 77),
	(12, 'Проект 3', 'site3.com', '2017-07-14 00:00:00', 84);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping data for table devteam.project_employee: ~8 rows (approximately)
/*!40000 ALTER TABLE `project_employee` DISABLE KEYS */;
INSERT INTO `project_employee` (`project_id`, `employee_id`, `hours`) VALUES
	(10, 1, 0),
	(10, 3, 0),
	(10, 4, 6),
	(10, 5, 0),
	(11, 19, 0),
	(11, 18, 0),
	(11, 22, 0),
	(12, 6, 0);
/*!40000 ALTER TABLE `project_employee` ENABLE KEYS */;

-- Dumping data for table devteam.qualification: ~7 rows (approximately)
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

-- Dumping data for table devteam.user: ~10 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
	(1, 'customer', '$2a$10$0qjCp.AhvMUWeJgOZ/bx/.39KewfxOEZriOwMBFzxzURGDfs.cutS', 'CUSTOMER'),
	(2, 'manager', '$2a$10$0qjCp.AhvMUWeJgOZ/bx/.39KewfxOEZriOwMBFzxzURGDfs.cutS', 'MANAGER'),
	(3, 'dev', '$2a$10$0qjCp.AhvMUWeJgOZ/bx/.39KewfxOEZriOwMBFzxzURGDfs.cutS', 'DEVELOPER'),
	(4, 'admin', '$2a$10$0qjCp.AhvMUWeJgOZ/bx/.39KewfxOEZriOwMBFzxzURGDfs.cutS', 'ADMIN'),
	(5, '22222222', '$2a$10$FiSPBsz3/NKTsjOIWmehlOVEX/PAsg3wNAvamWoyviO4HMTcvlHxW', 'MANAGER'),
	(6, 'patiya', '$2a$10$6RmazGWfbwOBH4TZCCRuNO0GP9HZemQ7k2luCHlpFWRSVn39hRTf.', 'MANAGER'),
	(7, 'lalallala', '$2a$10$wd11nmIC1OLLvXmhDQp9.ehE5Fdkiv5/YTC6JpL6oZTQO1eu7kDwS', 'MANAGER'),
	(8, 'fffffff', '$2a$10$wWgWUFWzQ01BXAWRgO8DQ.0Wa8IRof7KHhzyqSWP.f2C6CCCFx.7C', 'MANAGER'),
	(9, 'sfsfsf', '$2a$10$0qjCp.AhvMUWeJgOZ/bx/.39KewfxOEZriOwMBFzxzURGDfs.cutS', 'MANAGER'),
	(10, 'qqqqqqqqqqq', '$2a$10$N7B3jUKpos09HjZJZvi1L.laVQo/KWZua6er0hcBTtUHKAbhSEAwu', 'MANAGER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping data for table devteam.work: ~4 rows (approximately)
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
