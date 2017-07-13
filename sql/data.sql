
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `user_id`) VALUES
	(1, 'Васильев Василий Иванович', 'mail@mail', '+26262626262', 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `name`, `start_work`, `qualification_id`, `user_id`) VALUES
	(1, 'Иванов Иван', '2017-06-04 21:59:27', 1, 2),
	(2, 'Петров Иван', '2017-06-10 21:30:25', 4, NULL),
	(3, 'Петров Петр', '2017-06-29 21:39:41', 2, NULL),
	(4, 'Иванов Петр', '2017-06-29 21:39:51', 3, 3),
	(5, 'Сидоров Петр', '2017-06-29 21:40:00', 5, NULL),
	(6, 'Сидоров Иван', '2017-06-29 21:40:07', 6, NULL),
	(7, 'Просто Катя', '2017-07-10 22:59:48', 7, 4),
	(17, 'Федоров Иван', '2017-07-13 12:24:08', 1, NULL),
	(18, 'Федоров Петр', '2017-07-13 12:24:33', 4, NULL),
	(19, 'Иванов Федор', '2017-07-13 12:24:51', 2, NULL),
	(20, 'Петров Федор', '2017-07-13 12:25:16', 3, NULL),
	(21, 'Сидоров Федор', '2017-07-13 12:25:53', 5, NULL),
	(22, 'Сидоров Александр', '2017-07-13 12:27:45', 6, NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

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

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
	(1, 'customer', '', 'CUSTOMER'),
	(2, 'manager', '', 'MANAGER'),
	(3, 'dev', '', 'DEVELOPER'),
	(4, 'admin', '', 'ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` (`id`, `title`, `description`) VALUES
	(1, 'Дизайн', NULL),
	(2, 'Верстка', NULL),
	(3, 'Front end', NULL),
	(4, 'Программирование', NULL);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
