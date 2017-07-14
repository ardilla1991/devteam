SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `devteam`
--

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `user_id`) VALUES
(1, 'Васильев Василий Иванович', 'mail@test.com', '+125172222222', 1);

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `start_work`, `qualification_id`, `user_id`) VALUES
(1, 'Иванов Иван', '2017-06-04 18:59:27', 1, 2),
(2, 'Петров Иван', '2017-06-10 18:30:25', 4, NULL),
(3, 'Петров Петр', '2017-06-29 18:39:41', 2, NULL),
(4, 'Иванов Петр', '2017-06-29 18:39:51', 3, 3),
(5, 'Сидоров Петр', '2017-06-29 18:40:00', 5, NULL),
(6, 'Сидоров Иван', '2017-06-29 18:40:07', 6, NULL),
(7, 'Просто Катя', '2017-07-10 19:59:48', 7, 4),
(17, 'Федоров Иван', '2017-07-13 09:24:08', 1, NULL),
(18, 'Федоров Петр', '2017-07-13 09:24:33', 4, NULL),
(19, 'Иванов Федор', '2017-07-13 09:24:51', 2, NULL),
(20, 'Петров Федор', '2017-07-13 09:25:16', 3, NULL),
(21, 'Сидоров Федор', '2017-07-13 09:25:53', 5, NULL),
(22, 'Сидоров Александр', '2017-07-13 09:27:45', 6, NULL);

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id`, `title`, `description`, `specification`, `customer_id`, `dateCreated`, `dateStart`, `dateFinish`, `dateProcessing`, `price`) VALUES
(76, 'Проект 1', 'site1.com', 'ТЗ1.odt', 1, '2017-07-13 00:00:00', '2017-11-30 00:00:00', '2018-04-18 00:00:00', NULL, '3.00'),
(77, 'Проект 2', 'site2.com', 'ТЗ2.odt', 1, '2017-07-13 00:00:00', '2017-07-20 00:00:00', '2017-09-29 00:00:00', NULL, '300.00'),
(78, 'Сайт 1', 'project.com', 'ТЗ4.odt', 1, '2017-07-13 00:00:00', '2017-08-19 00:00:00', '2017-08-30 00:00:00', NULL, NULL),
(79, 'Сайт 2', 'site.com', 'ТЗ5.odt', 1, '2017-07-13 00:00:00', '2017-08-12 00:00:00', '2017-08-24 00:00:00', NULL, NULL),
(80, 'Сайт 3', 'siteN.com', 'ТЗ6.odt', 1, '2017-07-13 00:00:00', '2017-10-21 00:00:00', '2017-12-23 00:00:00', NULL, NULL),
(81, 'Сайт 4', 'site12.com', 'ТЗ7.odt', 1, '2017-07-13 00:00:00', '2017-12-02 00:00:00', '2018-05-23 00:00:00', NULL, NULL),
(82, 'Сайт 5', 'site5.com', 'ТЗ8.odt', 1, '2017-07-13 00:00:00', '2017-12-16 00:00:00', '2018-04-26 00:00:00', NULL, NULL),
(83, 'Сайт 6', 'site4.com', 'ТЗ9.odt', 1, '2017-07-13 00:00:00', '2017-07-31 00:00:00', '2017-08-26 00:00:00', NULL, NULL),
(84, 'Проект 3', 'site3.com', 'ТЗ3.odt', 1, '2017-07-14 00:00:00', '2017-07-14 00:00:00', '2017-07-28 00:00:00', '2017-07-14 00:00:00', '12.00');

--
-- Dumping data for table `order_qualification`
--

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
(84, 6, 1);

--
-- Dumping data for table `order_work`
--

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
(84, 4, NULL);

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`id`, `title`, `description`, `dateCreated`, `order_id`) VALUES
(10, 'Проект 1', 'site1.com', '2017-07-14 12:32:21', 76),
(11, 'Проект 2', 'site2.com', '2017-07-14 12:32:21', 77),
(12, 'Проект 3', 'site3.com', '2017-07-14 00:00:00', 84);

--
-- Dumping data for table `project_employee`
--

INSERT INTO `project_employee` (`project_id`, `employee_id`, `hours`) VALUES
(10, 1, 0),
(10, 3, 0),
(10, 4, 6),
(10, 5, 0),
(11, 19, 0),
(11, 18, 0),
(11, 22, 0),
(12, 6, 0);

--
-- Dumping data for table `qualification`
--

INSERT INTO `qualification` (`id`, `title`, `service`) VALUES
(1, 'manager', 0),
(2, 'disigner', 0),
(3, 'front-end developer', 0),
(4, 'junior java developer', 0),
(5, 'middle java developer', 0),
(6, 'senior java developer', 0),
(7, 'admin', 1);

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
(1, 'customer', '$2a$10$zBNfpHh0ZqErueXZCR/XzuZlRQGmOmkSU8ZqakwgMCpCjbo0SG/qW', 'CUSTOMER'),
(2, 'manager', '$2a$10$WrC53OHV6D/3CrjznATTmel2rlJif.j05d0QXxhBlEA6XX/AeHsHa', 'MANAGER'),
(3, 'dev', '$2a$10$GuRoYflaEACL/fAk5LFgCOfTUv0nwj9EAHNINoCnuAMDoTMKhRxue', 'DEVELOPER'),
(4, 'admin', '$2a$10$WrC53OHV6D/3CrjznATTmel2rlJif.j05d0QXxhBlEA6XX/AeHsHa', 'ADMIN');

--
-- Dumping data for table `work`
--

INSERT INTO `work` (`id`, `title`, `description`) VALUES
(1, 'Дизайн', NULL),
(2, 'Верстка', NULL),
(3, 'Front end', NULL),
(4, 'Программирование', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
