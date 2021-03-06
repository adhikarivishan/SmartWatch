-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2020 at 02:46 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_smart_watch`
--

-- --------------------------------------------------------

--
-- Table structure for table `fitbit_config`
--

CREATE TABLE `fitbit_config` (
  `id` int(11) NOT NULL,
  `fitbit_username` varchar(300) NOT NULL,
  `fitbit_password` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fitbit_config`
--

INSERT INTO `fitbit_config` (`id`, `fitbit_username`, `fitbit_password`, `user_id`) VALUES
(1, 'jarusshilpakar@gmail.com', 'jarusony', 1),
(2, 'jarusshilpakar@gmail.com', 'jarusony', 2);

-- --------------------------------------------------------

--
-- Table structure for table `fitbit_data`
--

CREATE TABLE `fitbit_data` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `foot_steps` int(11) NOT NULL,
  `distance` double NOT NULL,
  `calories` int(11) NOT NULL,
  `heart_rate` int(11) NOT NULL,
  `floors` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fitbit_data`
--

INSERT INTO `fitbit_data` (`id`, `user_id`, `date`, `foot_steps`, `distance`, `calories`, `heart_rate`, `floors`) VALUES
(1, 1, '2020-04-05', 1214, 0.91, 1454, 56, 0),
(2, 1, '2020-04-05', 1214, 0.91, 1455, 56, 0),
(3, 1, '2020-04-05', 1270, 0.95, 1476, 56, 0),
(4, 1, '2020-04-06', 591, 0.44, 1090, 56, 0),
(5, 1, '2020-04-06', 591, 0.44, 1099, 56, 0),
(6, 1, '2020-04-06', 591, 0.44, 1124, 56, 0),
(7, 1, '2020-04-06', 591, 0.44, 1125, 56, 0),
(8, 1, '2020-04-06', 591, 0.44, 1125, 56, 0),
(9, 1, '2020-04-06', 591, 0.44, 1127, 56, 0),
(10, 1, '2020-04-06', 591, 0.44, 1198, 56, 0),
(11, 1, '2020-04-06', 591, 0.44, 1200, 56, 0),
(12, 1, '2020-04-06', 591, 0.44, 1203, 56, 0),
(13, 1, '2020-04-06', 591, 0.44, 1204, 56, 0),
(14, 1, '2020-04-06', 591, 0.44, 1206, 56, 0),
(15, 1, '2020-04-06', 591, 0.44, 1208, 56, 0),
(16, 1, '2020-04-06', 591, 0.44, 1210, 56, 0),
(17, 1, '2020-04-06', 591, 0.44, 1211, 56, 0),
(18, 1, '2020-04-06', 591, 0.44, 1212, 56, 0),
(19, 1, '2020-04-06', 591, 0.44, 1213, 56, 0),
(20, 1, '2020-04-06', 591, 0.44, 1214, 56, 0),
(21, 1, '2020-04-06', 591, 0.44, 1214, 56, 0),
(22, 1, '2020-04-06', 591, 0.44, 1306, 56, 0),
(23, 1, '2020-04-06', 591, 0.44, 1307, 56, 0),
(24, 1, '2020-04-06', 591, 0.44, 1313, 56, 0),
(25, 1, '2020-04-06', 591, 0.44, 1323, 56, 0),
(26, 1, '2020-04-06', 591, 0.44, 1328, 56, 0),
(27, 1, '2020-04-06', 591, 0.44, 1336, 56, 0),
(28, 1, '2020-04-06', 591, 0.44, 1337, 56, 0),
(29, 1, '2020-04-06', 591, 0.44, 1344, 56, 0),
(30, 1, '2020-04-07', 626, 0.47, 873, 57, 0),
(31, 1, '2020-04-07', 626, 0.47, 876, 57, 0),
(32, 1, '2020-04-07', 663, 0.5, 952, 56, 0),
(33, 1, '2020-04-07', 663, 0.5, 959, 56, 0),
(34, 1, '2020-04-07', 663, 0.5, 962, 56, 0),
(35, 1, '2020-04-07', 663, 0.5, 963, 56, 0),
(36, 1, '2020-04-07', 663, 0.5, 971, 56, 0),
(37, 1, '2020-04-07', 663, 0.5, 975, 56, 0),
(38, 1, '2020-04-07', 663, 0.5, 976, 56, 0),
(39, 1, '2020-04-07', 663, 0.5, 980, 56, 0),
(40, 1, '2020-04-07', 663, 0.5, 981, 56, 0),
(41, 1, '2020-04-07', 663, 0.5, 982, 56, 0),
(42, 1, '2020-04-07', 663, 0.5, 983, 56, 0),
(43, 1, '2020-04-07', 663, 0.5, 984, 56, 0),
(44, 1, '2020-04-07', 663, 0.5, 986, 56, 0),
(45, 1, '2020-04-07', 663, 0.5, 987, 56, 0),
(46, 1, '2020-04-07', 663, 0.5, 988, 56, 0),
(47, 1, '2020-04-07', 663, 0.5, 989, 56, 0),
(48, 1, '2020-04-07', 663, 0.5, 990, 56, 0),
(49, 1, '2020-04-07', 663, 0.5, 992, 56, 0),
(50, 1, '2020-04-07', 663, 0.5, 993, 56, 0),
(51, 1, '2020-04-07', 663, 0.5, 994, 56, 0),
(52, 1, '2020-04-07', 663, 0.5, 995, 56, 0),
(53, 1, '2020-04-07', 663, 0.5, 996, 56, 0),
(54, 1, '2020-04-07', 663, 0.5, 998, 56, 0),
(55, 1, '2020-04-07', 773, 0.58, 1014, 56, 0),
(56, 1, '2020-04-07', 773, 0.58, 1015, 56, 0),
(57, 1, '2020-04-07', 773, 0.58, 1016, 56, 0),
(58, 1, '2020-04-07', 773, 0.58, 1018, 56, 0),
(59, 1, '2020-04-07', 773, 0.58, 1019, 56, 0),
(60, 1, '2020-04-07', 773, 0.58, 1020, 56, 0),
(61, 1, '2020-04-07', 773, 0.58, 1021, 56, 0),
(62, 1, '2020-04-07', 773, 0.58, 1022, 56, 0),
(63, 1, '2020-04-07', 773, 0.58, 1024, 56, 0),
(64, 1, '2020-04-07', 773, 0.58, 1025, 56, 0),
(65, 1, '2020-04-07', 773, 0.58, 1026, 56, 0),
(66, 1, '2020-04-07', 773, 0.58, 1027, 56, 0),
(67, 1, '2020-04-07', 773, 0.58, 1028, 56, 0),
(68, 1, '2020-04-07', 773, 0.58, 1030, 56, 0),
(69, 1, '2020-04-07', 773, 0.58, 1031, 56, 0),
(70, 1, '2020-04-07', 773, 0.58, 1032, 56, 0),
(71, 1, '2020-04-07', 798, 0.6, 1040, 56, 0),
(72, 1, '2020-04-07', 798, 0.6, 1041, 56, 0),
(73, 1, '2020-04-07', 798, 0.6, 1042, 56, 0),
(74, 1, '2020-04-07', 798, 0.6, 1044, 56, 0),
(75, 1, '2020-04-07', 798, 0.6, 1045, 56, 0),
(76, 1, '2020-04-07', 798, 0.6, 1048, 56, 0),
(77, 1, '2020-04-07', 798, 0.6, 1049, 56, 0),
(78, 1, '2020-04-07', 798, 0.6, 1054, 56, 0),
(79, 1, '2020-04-07', 798, 0.6, 1068, 56, 0),
(80, 1, '2020-04-07', 798, 0.6, 1069, 56, 0),
(81, 1, '2020-04-07', 798, 0.6, 1071, 56, 0),
(82, 1, '2020-04-07', 798, 0.6, 1072, 56, 0),
(83, 1, '2020-04-07', 798, 0.6, 1073, 56, 0),
(84, 1, '2020-04-07', 798, 0.6, 1074, 56, 0),
(85, 2, '2020-04-07', 798, 0.6, 1077, 56, 0),
(86, 2, '2020-04-07', 798, 0.6, 1077, 56, 0),
(87, 2, '2020-04-07', 798, 0.6, 1086, 56, 0),
(88, 1, '2020-04-07', 798, 0.6, 1089, 56, 0),
(89, 1, '2020-04-07', 964, 0.72, 1117, 56, 0),
(90, 1, '2020-04-07', 964, 0.72, 1129, 56, 0),
(91, 1, '2020-04-07', 964, 0.72, 1132, 56, 0),
(92, 1, '2020-04-07', 964, 0.72, 1134, 56, 0),
(93, 1, '2020-04-07', 964, 0.72, 1135, 56, 0),
(94, 1, '2020-04-07', 964, 0.72, 1136, 56, 0),
(95, 1, '2020-04-07', 964, 0.72, 1139, 56, 0),
(96, 1, '2020-04-07', 964, 0.72, 1140, 56, 0),
(97, 1, '2020-04-07', 964, 0.72, 1142, 56, 0),
(98, 1, '2020-04-07', 964, 0.72, 1144, 56, 0),
(99, 1, '2020-04-07', 1000, 0.75, 1202, 56, 0),
(100, 1, '2020-04-07', 1042, 0.78, 1248, 56, 0),
(101, 1, '2020-04-07', 1042, 0.78, 1251, 56, 0),
(102, 1, '2020-04-07', 1042, 0.78, 1252, 56, 0),
(103, 1, '2020-04-07', 1388, 1.04, 1467, 56, 0),
(104, 1, '2020-04-07', 1388, 1.04, 1470, 56, 0),
(105, 1, '2020-04-07', 1388, 1.04, 1471, 56, 0),
(106, 1, '2020-04-07', 1388, 1.04, 1482, 56, 0),
(107, 1, '2020-04-07', 1388, 1.04, 1531, 56, 0),
(108, 1, '2020-04-07', 1388, 1.04, 1533, 56, 0),
(109, 1, '2020-04-07', 1388, 1.04, 1534, 56, 0),
(110, 1, '2020-04-07', 1388, 1.04, 1537, 56, 0),
(111, 1, '2020-04-08', 370, 0.28, 894, 54, 0),
(112, 1, '2020-04-08', 370, 0.28, 898, 54, 0),
(113, 1, '2020-04-08', 370, 0.28, 898, 54, 0),
(114, 1, '2020-04-08', 370, 0.28, 908, 54, 0),
(115, 1, '2020-04-08', 370, 0.28, 909, 54, 0),
(116, 1, '2020-04-08', 370, 0.28, 915, 54, 0),
(117, 1, '2020-04-08', 407, 0.3, 926, 54, 0),
(118, 1, '2020-04-08', 407, 0.3, 928, 54, 0),
(119, 1, '2020-04-08', 407, 0.3, 930, 54, 0),
(120, 1, '2020-04-08', 407, 0.3, 932, 54, 0),
(121, 1, '2020-04-08', 407, 0.3, 940, 54, 0),
(122, 1, '2020-04-08', 407, 0.3, 942, 54, 0),
(123, 1, '2020-04-08', 412, 0.31, 956, 54, 0),
(124, 1, '2020-04-08', 494, 0.37, 1044, 55, 0),
(125, 1, '2020-04-08', 494, 0.37, 1046, 55, 0),
(126, 1, '2020-04-08', 494, 0.37, 1048, 55, 0),
(127, 1, '2020-04-08', 494, 0.37, 1050, 55, 0),
(128, 1, '2020-04-08', 494, 0.37, 1052, 55, 0),
(129, 1, '2020-04-08', 494, 0.37, 1054, 55, 0),
(130, 1, '2020-04-08', 494, 0.37, 1054, 55, 0),
(131, 1, '2020-04-08', 502, 0.38, 1068, 55, 0),
(132, 1, '2020-04-08', 564, 0.42, 1155, 55, 0),
(133, 2, '2020-04-08', 564, 0.42, 1155, 55, 0),
(134, 2, '2020-04-08', 564, 0.42, 1156, 55, 0),
(135, 2, '2020-04-08', 564, 0.42, 1159, 55, 0),
(136, 1, '2020-04-08', 576, 0.43, 1188, 55, 0),
(137, 2, '2020-04-08', 576, 0.43, 1188, 55, 0),
(138, 2, '2020-04-08', 576, 0.43, 1189, 55, 0),
(139, 2, '2020-04-08', 641, 0.48, 1204, 55, 0),
(140, 2, '2020-04-08', 641, 0.48, 1210, 55, 0),
(141, 2, '2020-04-08', 641, 0.48, 1215, 55, 0),
(142, 2, '2020-04-08', 641, 0.48, 1215, 55, 0),
(143, 2, '2020-04-08', 641, 0.48, 1231, 55, 0),
(144, 2, '2020-04-08', 641, 0.48, 1231, 55, 0),
(145, 1, '2020-04-08', 641, 0.48, 1242, 55, 0),
(146, 1, '2020-04-08', 697, 0.52, 1254, 55, 0),
(147, 1, '2020-04-08', 697, 0.52, 1257, 55, 0),
(148, 1, '2020-04-08', 697, 0.52, 1257, 55, 0),
(149, 1, '2020-04-08', 697, 0.52, 1260, 55, 0),
(150, 1, '2020-04-08', 697, 0.52, 1261, 55, 0),
(151, 1, '2020-04-08', 697, 0.52, 1263, 55, 0),
(152, 1, '2020-04-08', 697, 0.52, 1265, 55, 0),
(153, 1, '2020-04-08', 697, 0.52, 1265, 55, 0),
(154, 2, '2020-04-08', 697, 0.52, 1267, 55, 0),
(155, 2, '2020-04-08', 697, 0.52, 1269, 55, 0),
(156, 2, '2020-04-08', 697, 0.52, 1269, 55, 0),
(157, 2, '2020-04-08', 697, 0.52, 1271, 55, 0),
(158, 2, '2020-04-08', 697, 0.52, 1274, 55, 0),
(159, 2, '2020-04-08', 697, 0.52, 1274, 55, 0),
(160, 2, '2020-04-08', 697, 0.52, 1275, 55, 0),
(161, 2, '2020-04-08', 697, 0.52, 1277, 55, 0),
(162, 2, '2020-04-08', 697, 0.52, 1278, 55, 0),
(163, 2, '2020-04-08', 697, 0.52, 1279, 55, 0),
(164, 2, '2020-04-08', 697, 0.52, 1280, 55, 0),
(165, 2, '2020-04-08', 697, 0.52, 1281, 55, 0),
(166, 2, '2020-04-08', 697, 0.52, 1283, 55, 0),
(167, 2, '2020-04-08', 697, 0.52, 1284, 55, 0),
(168, 2, '2020-04-08', 697, 0.52, 1285, 55, 0),
(169, 2, '2020-04-08', 697, 0.52, 1286, 55, 0),
(170, 2, '2020-04-08', 697, 0.52, 1287, 55, 0),
(171, 2, '2020-04-08', 697, 0.52, 1289, 55, 0),
(172, 2, '2020-04-08', 697, 0.52, 1290, 55, 0),
(173, 2, '2020-04-08', 697, 0.52, 1291, 55, 0),
(174, 2, '2020-04-08', 697, 0.52, 1295, 55, 0),
(175, 2, '2020-04-08', 697, 0.52, 1296, 55, 0),
(176, 2, '2020-04-08', 697, 0.52, 1297, 55, 0),
(177, 2, '2020-04-08', 697, 0.52, 1298, 55, 0),
(178, 2, '2020-04-08', 697, 0.52, 1299, 55, 0),
(179, 2, '2020-04-08', 697, 0.52, 1300, 55, 0),
(180, 2, '2020-04-08', 697, 0.52, 1302, 55, 0),
(181, 2, '2020-04-08', 697, 0.52, 1303, 55, 0),
(182, 2, '2020-04-08', 697, 0.52, 1304, 55, 0),
(183, 2, '2020-04-08', 697, 0.52, 1305, 55, 0),
(184, 2, '2020-04-08', 697, 0.52, 1306, 55, 0),
(185, 1, '2020-04-08', 748, 0.56, 1364, 55, 0),
(186, 1, '2020-04-08', 748, 0.56, 1366, 55, 0),
(187, 2, '2020-04-08', 825, 0.62, 1418, 55, 0),
(188, 2, '2020-04-08', 825, 0.62, 1424, 55, 0),
(189, 2, '2020-04-08', 825, 0.62, 1425, 55, 0),
(190, 2, '2020-04-08', 825, 0.62, 1430, 55, 0),
(191, 2, '2020-04-08', 825, 0.62, 1430, 55, 0),
(192, 2, '2020-04-08', 825, 0.62, 1434, 55, 0),
(193, 2, '2020-04-08', 825, 0.62, 1443, 55, 0),
(194, 2, '2020-04-08', 825, 0.62, 1486, 55, 0),
(195, 2, '2020-04-08', 825, 0.62, 1486, 55, 0),
(196, 1, '2020-04-10', 700, 0.52, 991, 54, 0),
(197, 1, '2020-04-10', 700, 0.52, 1038, 54, 0),
(198, 1, '2020-04-10', 700, 0.52, 1039, 54, 0),
(199, 1, '2020-04-10', 891, 0.67, 1153, 54, 0),
(200, 1, '2020-04-10', 891, 0.67, 1158, 54, 0),
(201, 1, '2020-04-10', 891, 0.67, 1164, 54, 0),
(202, 1, '2020-04-10', 891, 0.67, 1168, 54, 0),
(203, 1, '2020-04-10', 891, 0.67, 1184, 54, 0),
(204, 1, '2020-04-10', 891, 0.67, 1202, 54, 0),
(205, 1, '2020-04-10', 891, 0.67, 1204, 54, 0),
(206, 1, '2020-04-10', 891, 0.67, 1277, 54, 0),
(207, 1, '2020-04-10', 891, 0.67, 1308, 54, 0),
(208, 1, '2020-04-10', 891, 0.67, 1312, 54, 0),
(209, 1, '2020-04-10', 891, 0.67, 1312, 54, 0),
(210, 1, '2020-04-10', 891, 0.67, 1312, 54, 0),
(211, 1, '2020-04-10', 891, 0.67, 1317, 54, 0),
(212, 1, '2020-04-10', 891, 0.67, 1317, 54, 0),
(213, 1, '2020-04-14', 0, 0, 999, 0, 0),
(214, 1, '2020-04-14', 0, 0, 1001, 0, 0),
(215, 1, '2020-04-15', 0, 0, 1036, 0, 0),
(216, 1, '2020-04-15', 0, 0, 1038, 0, 0),
(217, 1, '2020-04-15', 0, 0, 1038, 0, 0),
(218, 1, '2020-04-15', 0, 0, 1039, 0, 0),
(219, 1, '2020-04-15', 0, 0, 1039, 0, 0),
(220, 1, '2020-04-15', 0, 0, 1039, 0, 0),
(221, 1, '2020-04-15', 0, 0, 1040, 0, 0),
(222, 1, '2020-04-15', 0, 0, 1040, 0, 0),
(223, 1, '2020-04-15', 0, 0, 1040, 0, 0),
(224, 1, '2020-04-15', 0, 0, 1040, 0, 0),
(225, 1, '2020-04-15', 0, 0, 1041, 0, 0),
(226, 1, '2020-04-15', 0, 0, 1041, 0, 0),
(227, 1, '2020-04-15', 0, 0, 1042, 0, 0),
(228, 1, '2020-04-15', 0, 0, 1044, 0, 0),
(229, 1, '2020-04-15', 0, 0, 1046, 0, 0),
(230, 1, '2020-04-15', 0, 0, 1047, 0, 0),
(231, 1, '2020-04-15', 0, 0, 1047, 0, 0),
(232, 1, '2020-04-15', 0, 0, 1047, 0, 0),
(233, 1, '2020-04-15', 0, 0, 1048, 0, 0),
(234, 1, '2020-04-15', 0, 0, 1048, 0, 0),
(235, 1, '2020-04-15', 0, 0, 1095, 0, 0),
(236, 1, '2020-04-16', 294, 0.22, 891, 54, 3),
(237, 1, '2020-04-16', 294, 0.22, 893, 54, 3),
(238, 1, '2020-04-16', 294, 0.22, 893, 54, 3),
(239, 1, '2020-04-16', 294, 0.22, 893, 54, 3),
(240, 1, '2020-04-16', 294, 0.22, 893, 54, 3),
(241, 1, '2020-04-16', 294, 0.22, 894, 54, 3),
(242, 1, '2020-04-16', 294, 0.22, 894, 54, 3),
(243, 1, '2020-04-16', 294, 0.22, 894, 54, 3),
(244, 1, '2020-04-16', 601, 0.45, 987, 55, 0),
(245, 1, '2020-04-16', 601, 0.45, 987, 55, 0),
(246, 1, '2020-04-16', 601, 0.45, 988, 55, 0),
(247, 1, '2020-04-16', 601, 0.45, 989, 55, 0),
(248, 1, '2020-04-19', 0, 0, 889, 0, 0),
(249, 1, '2020-04-19', 0, 0, 891, 0, 0),
(250, 1, '2020-04-19', 0, 0, 896, 0, 0),
(251, 1, '2020-04-19', 0, 0, 897, 0, 0),
(252, 1, '2020-04-19', 0, 0, 898, 0, 0),
(253, 1, '2020-04-19', 0, 0, 899, 0, 0),
(254, 1, '2020-04-19', 0, 0, 901, 0, 0),
(255, 1, '2020-04-19', 0, 0, 902, 0, 0),
(256, 1, '2020-04-19', 0, 0, 905, 0, 0),
(257, 1, '2020-04-19', 0, 0, 906, 0, 0),
(258, 1, '2020-04-19', 0, 0, 908, 0, 0),
(259, 1, '2020-04-19', 0, 0, 912, 0, 0),
(260, 1, '2020-04-19', 0, 0, 912, 0, 0),
(261, 1, '2020-04-19', 0, 0, 920, 0, 0),
(262, 1, '2020-04-19', 0, 0, 921, 0, 0),
(263, 1, '2020-04-19', 0, 0, 921, 0, 0),
(264, 1, '2020-04-19', 0, 0, 922, 0, 0),
(265, 1, '2020-04-19', 0, 0, 924, 0, 0),
(266, 1, '2020-04-19', 0, 0, 927, 0, 0),
(267, 1, '2020-04-19', 0, 0, 933, 0, 0),
(268, 1, '2020-04-19', 0, 0, 934, 0, 0),
(269, 1, '2020-04-19', 0, 0, 935, 0, 0),
(270, 1, '2020-04-19', 0, 0, 946, 0, 0),
(271, 1, '2020-04-19', 0, 0, 947, 0, 0),
(272, 1, '2020-04-19', 0, 0, 957, 0, 0),
(273, 1, '2020-04-19', 0, 0, 962, 0, 0),
(274, 1, '2020-04-19', 0, 0, 965, 0, 0),
(275, 1, '2020-04-19', 0, 0, 966, 0, 0),
(276, 1, '2020-04-19', 0, 0, 966, 0, 0),
(277, 1, '2020-04-19', 0, 0, 973, 0, 0),
(278, 1, '2020-04-19', 0, 0, 974, 0, 0),
(279, 1, '2020-04-19', 0, 0, 976, 0, 0),
(280, 1, '2020-04-19', 0, 0, 976, 0, 0),
(281, 1, '2020-04-19', 0, 0, 984, 0, 0),
(282, 1, '2020-04-19', 0, 0, 992, 0, 0),
(283, 1, '2020-04-19', 0, 0, 993, 0, 0),
(284, 1, '2020-04-19', 0, 0, 998, 0, 0),
(285, 1, '2020-04-19', 0, 0, 999, 0, 0),
(286, 1, '2020-04-19', 0, 0, 999, 0, 0),
(287, 1, '2020-04-19', 0, 0, 1011, 0, 0),
(288, 1, '2020-04-19', 0, 0, 1011, 0, 0),
(289, 1, '2020-04-19', 0, 0, 1013, 0, 0),
(290, 1, '2020-04-19', 0, 0, 1013, 0, 0),
(291, 1, '2020-04-19', 0, 0, 1013, 0, 0),
(292, 1, '2020-04-19', 0, 0, 1013, 0, 0),
(293, 1, '2020-04-19', 0, 0, 1013, 0, 0),
(294, 1, '2020-04-19', 0, 0, 1014, 0, 0),
(295, 1, '2020-04-19', 0, 0, 1014, 0, 0),
(296, 1, '2020-04-19', 0, 0, 1014, 0, 0),
(297, 1, '2020-04-19', 0, 0, 1014, 0, 0),
(298, 1, '2020-04-19', 0, 0, 1018, 0, 0),
(299, 1, '2020-04-19', 0, 0, 1046, 0, 0),
(300, 1, '2020-04-19', 0, 0, 1047, 0, 0),
(301, 1, '2020-04-19', 0, 0, 1048, 0, 0),
(302, 1, '2020-04-19', 0, 0, 1049, 0, 0),
(303, 1, '2020-04-19', 0, 0, 1051, 0, 0),
(304, 1, '2020-04-19', 0, 0, 1052, 0, 0),
(305, 1, '2020-04-19', 0, 0, 1053, 0, 0),
(306, 1, '2020-04-19', 0, 0, 1054, 0, 0),
(307, 1, '2020-04-19', 0, 0, 1055, 0, 0),
(308, 1, '2020-04-19', 0, 0, 1057, 0, 0),
(309, 1, '2020-04-19', 0, 0, 1058, 0, 0),
(310, 1, '2020-04-19', 0, 0, 1059, 0, 0),
(311, 1, '2020-04-19', 0, 0, 1060, 0, 0),
(312, 1, '2020-04-19', 0, 0, 1061, 0, 0),
(313, 1, '2020-04-19', 0, 0, 1063, 0, 0),
(314, 1, '2020-04-19', 0, 0, 1064, 0, 0),
(315, 1, '2020-04-19', 0, 0, 1065, 0, 0),
(316, 1, '2020-04-19', 0, 0, 1066, 0, 0),
(317, 1, '2020-04-19', 0, 0, 1067, 0, 0),
(318, 1, '2020-04-19', 0, 0, 1069, 0, 0),
(319, 1, '2020-04-19', 0, 0, 1070, 0, 0),
(320, 1, '2020-04-19', 0, 0, 1071, 0, 0),
(321, 1, '2020-04-19', 0, 0, 1072, 0, 0),
(322, 1, '2020-04-19', 0, 0, 1073, 0, 0),
(323, 1, '2020-04-19', 0, 0, 1075, 0, 0),
(324, 1, '2020-04-19', 0, 0, 1076, 0, 0),
(325, 1, '2020-04-19', 0, 0, 1077, 0, 0),
(326, 1, '2020-04-19', 0, 0, 1078, 0, 0),
(327, 1, '2020-04-19', 0, 0, 1079, 0, 0),
(328, 1, '2020-04-19', 0, 0, 1080, 0, 0),
(329, 1, '2020-04-19', 0, 0, 1082, 0, 0),
(330, 1, '2020-04-19', 0, 0, 1083, 0, 0),
(331, 1, '2020-04-19', 0, 0, 1084, 0, 0),
(332, 1, '2020-04-19', 0, 0, 1085, 0, 0),
(333, 1, '2020-04-19', 0, 0, 1086, 0, 0),
(334, 1, '2020-04-19', 0, 0, 1088, 0, 0),
(335, 1, '2020-04-19', 0, 0, 1089, 0, 0),
(336, 1, '2020-04-19', 0, 0, 1090, 0, 0),
(337, 1, '2020-04-19', 0, 0, 1094, 0, 0),
(338, 1, '2020-04-19', 0, 0, 1096, 0, 0),
(339, 1, '2020-04-19', 0, 0, 1096, 0, 0),
(340, 1, '2020-04-19', 0, 0, 1097, 0, 0),
(341, 1, '2020-04-19', 0, 0, 1098, 0, 0),
(342, 1, '2020-04-19', 0, 0, 1100, 0, 0),
(343, 1, '2020-04-19', 0, 0, 1101, 0, 0),
(344, 1, '2020-04-19', 0, 0, 1102, 0, 0),
(345, 1, '2020-04-19', 0, 0, 1103, 0, 0),
(346, 1, '2020-04-19', 0, 0, 1104, 0, 0),
(347, 1, '2020-04-19', 0, 0, 1105, 0, 0),
(348, 1, '2020-04-19', 0, 0, 1107, 0, 0),
(349, 1, '2020-04-19', 0, 0, 1111, 0, 0),
(350, 1, '2020-04-19', 0, 0, 1111, 0, 0),
(351, 1, '2020-04-19', 0, 0, 1113, 0, 0),
(352, 1, '2020-04-19', 0, 0, 1113, 0, 0),
(353, 1, '2020-04-19', 0, 0, 1114, 0, 0),
(354, 1, '2020-04-19', 0, 0, 1115, 0, 0),
(355, 1, '2020-04-19', 0, 0, 1116, 0, 0),
(356, 1, '2020-04-19', 0, 0, 1117, 0, 0),
(357, 1, '2020-04-19', 0, 0, 1120, 0, 0),
(358, 1, '2020-04-19', 0, 0, 1121, 0, 0),
(359, 1, '2020-04-19', 0, 0, 1125, 0, 0),
(360, 1, '2020-04-19', 0, 0, 1128, 0, 0),
(361, 1, '2020-04-19', 0, 0, 1128, 0, 0),
(362, 1, '2020-04-19', 0, 0, 1129, 0, 0),
(363, 1, '2020-04-19', 0, 0, 1131, 0, 0),
(364, 1, '2020-04-19', 0, 0, 1137, 0, 0),
(365, 1, '2020-04-19', 0, 0, 1138, 0, 0),
(366, 1, '2020-04-19', 0, 0, 1139, 0, 0),
(367, 1, '2020-04-19', 0, 0, 1140, 0, 0),
(368, 1, '2020-04-19', 0, 0, 1145, 0, 0),
(369, 1, '2020-04-19', 0, 0, 1146, 0, 0),
(370, 1, '2020-04-19', 0, 0, 1150, 0, 0),
(371, 1, '2020-04-19', 0, 0, 1151, 0, 0),
(372, 1, '2020-04-19', 0, 0, 1151, 0, 0),
(373, 1, '2020-04-19', 0, 0, 1152, 0, 0),
(374, 1, '2020-04-19', 0, 0, 1152, 0, 0),
(375, 1, '2020-04-19', 0, 0, 1153, 0, 0),
(376, 1, '2020-04-19', 0, 0, 1153, 0, 0),
(377, 1, '2020-04-19', 0, 0, 1154, 0, 0),
(378, 1, '2020-04-19', 0, 0, 1154, 0, 0),
(379, 1, '2020-04-19', 0, 0, 1154, 0, 0),
(380, 1, '2020-04-19', 0, 0, 1154, 0, 0),
(381, 1, '2020-04-19', 0, 0, 1156, 0, 0),
(382, 1, '2020-04-19', 0, 0, 1157, 0, 0),
(383, 1, '2020-04-19', 0, 0, 1157, 0, 0),
(384, 1, '2020-04-19', 0, 0, 1157, 0, 0),
(385, 1, '2020-04-19', 0, 0, 1157, 0, 0),
(386, 1, '2020-04-19', 0, 0, 1158, 0, 0),
(387, 1, '2020-04-19', 0, 0, 1159, 0, 0),
(388, 1, '2020-04-19', 0, 0, 1160, 0, 0),
(389, 1, '2020-04-19', 0, 0, 1160, 0, 0),
(390, 1, '2020-04-19', 10, 0.01, 1170, 0, 0),
(391, 1, '2020-04-19', 10, 0.01, 1171, 0, 0),
(392, 1, '2020-04-19', 10, 0.01, 1173, 0, 0),
(393, 1, '2020-04-19', 10, 0.01, 1174, 0, 0),
(394, 1, '2020-04-19', 10, 0.01, 1175, 0, 0),
(395, 1, '2020-04-19', 10, 0.01, 1175, 0, 0),
(396, 1, '2020-04-19', 10, 0.01, 1175, 0, 0),
(397, 1, '2020-04-22', 4016, 3, 1707, 54, 9),
(398, 1, '2020-04-22', 4043, 3.02, 1713, 54, 9),
(399, 1, '2020-04-22', 4043, 3.02, 1715, 54, 9),
(400, 1, '2020-04-22', 4043, 3.02, 1716, 54, 9),
(401, 1, '2020-04-22', 4043, 3.02, 1716, 54, 9),
(402, 1, '2020-04-22', 4043, 3.02, 1716, 54, 9);

-- --------------------------------------------------------

--
-- Table structure for table `goals`
--

CREATE TABLE `goals` (
  `id` int(11) NOT NULL,
  `foot_steps` int(11) NOT NULL,
  `distance_traveled` int(11) NOT NULL,
  `calories_out` int(11) NOT NULL,
  `floors` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `goals`
--

INSERT INTO `goals` (`id`, `foot_steps`, `distance_traveled`, `calories_out`, `floors`, `user_id`) VALUES
(1, 100, 5, 1500, 20, 1);

-- --------------------------------------------------------

--
-- Table structure for table `trainee`
--

CREATE TABLE `trainee` (
  `trainer_id` int(11) NOT NULL,
  `trainee_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trainee`
--

INSERT INTO `trainee` (`trainer_id`, `trainee_id`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `address` varchar(300) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `email` varchar(300) NOT NULL,
  `password` varchar(200) NOT NULL,
  `account_type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `name`, `address`, `phone`, `email`, `password`, `account_type`) VALUES
(1, 'Suraj Shilpakar', 'bkt', '9813058440', 'suraj@gmail.com', 'suraj', 'trainer'),
(2, 'Saroj Shilpakar', 'ktm', '9813056023', 'saroj@gmail.com', '1234', 'trainee');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fitbit_config`
--
ALTER TABLE `fitbit_config`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fitbit_data`
--
ALTER TABLE `fitbit_data`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `goals`
--
ALTER TABLE `goals`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fitbit_config`
--
ALTER TABLE `fitbit_config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `fitbit_data`
--
ALTER TABLE `fitbit_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=403;

--
-- AUTO_INCREMENT for table `goals`
--
ALTER TABLE `goals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
