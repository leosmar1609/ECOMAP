CREATE DATABASE  IF NOT EXISTS `ecobanco` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecobanco`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: ecobanco
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administradores`
--

DROP TABLE IF EXISTS `administradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administradores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `nome` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administradores`
--

LOCK TABLES `administradores` WRITE;
/*!40000 ALTER TABLE `administradores` DISABLE KEYS */;
INSERT INTO `administradores` VALUES (1,'leo.marcos6440@gmail.com','75869470Leo.','Leonardo Marcos');
/*!40000 ALTER TABLE `administradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionarios` (
  `cpffunc` varchar(14) NOT NULL,
  `nomefunc` varchar(40) NOT NULL,
  `fonefunc` varchar(14) NOT NULL,
  `emailfunc` varchar(30) NOT NULL,
  `senhafunc` varchar(15) NOT NULL,
  `codpontocoleta` int NOT NULL,
  PRIMARY KEY (`cpffunc`),
  KEY `codpontocoleta` (`codpontocoleta`),
  CONSTRAINT `funcionarios_ibfk_1` FOREIGN KEY (`codpontocoleta`) REFERENCES `pontosdecoleta` (`codpontocoleta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES ('777.666.444-75','Leo do Grau','11111111111','l.d.s.m.lds@gmail.com','12344321',1),('99483757846','Zezinho da Silva','11965849305','zezinho.oi@gmail.com','09877890',2);
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pontosdecoleta`
--

DROP TABLE IF EXISTS `pontosdecoleta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pontosdecoleta` (
  `codpontocoleta` int NOT NULL AUTO_INCREMENT,
  `endpontocoleta` varchar(50) NOT NULL,
  `ceppontocoleta` varchar(9) NOT NULL,
  `bairropontocoleta` varchar(20) NOT NULL,
  PRIMARY KEY (`codpontocoleta`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontosdecoleta`
--

LOCK TABLES `pontosdecoleta` WRITE;
/*!40000 ALTER TABLE `pontosdecoleta` DISABLE KEYS */;
INSERT INTO `pontosdecoleta` VALUES (1,'Rua Gaivota, 16','06693560','Vitápolis'),(2,'Rua ABC, 14','04453245','Vitápolis');
/*!40000 ALTER TABLE `pontosdecoleta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rastreamento`
--

DROP TABLE IF EXISTS `rastreamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rastreamento` (
  `codresiduo` int NOT NULL,
  `tiporesiduo` varchar(20) NOT NULL,
  `quantcoletares` int NOT NULL,
  `codpontocoleta` int NOT NULL,
  KEY `codresiduo` (`codresiduo`),
  KEY `codpontocoleta` (`codpontocoleta`),
  CONSTRAINT `rastreamento_ibfk_1` FOREIGN KEY (`codresiduo`) REFERENCES `residuos` (`codresiduo`),
  CONSTRAINT `rastreamento_ibfk_2` FOREIGN KEY (`codpontocoleta`) REFERENCES `pontosdecoleta` (`codpontocoleta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rastreamento`
--

LOCK TABLES `rastreamento` WRITE;
/*!40000 ALTER TABLE `rastreamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `rastreamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `residuos`
--

DROP TABLE IF EXISTS `residuos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `residuos` (
  `codresiduo` int NOT NULL AUTO_INCREMENT,
  `tiporesiduo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `categoria` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descricao` varchar(300) NOT NULL,
  `descarte` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`codresiduo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `residuos`
--

LOCK TABLES `residuos` WRITE;
/*!40000 ALTER TABLE `residuos` DISABLE KEYS */;
INSERT INTO `residuos` VALUES (1,'Metal','Reciclavel','Metal é um ferro legal e faz parte do nosso dia a dia','Descartavel em ecopontos e ferro-velhos');
/*!40000 ALTER TABLE `residuos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voluntarios`
--

DROP TABLE IF EXISTS `voluntarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voluntarios` (
  `cpfvol` varchar(14) NOT NULL,
  `nomevol` varchar(40) NOT NULL,
  `fonevol` varchar(14) NOT NULL,
  `emailvol` varchar(30) NOT NULL,
  `senhavol` varchar(15) NOT NULL,
  PRIMARY KEY (`cpfvol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voluntarios`
--

LOCK TABLES `voluntarios` WRITE;
/*!40000 ALTER TABLE `voluntarios` DISABLE KEYS */;
INSERT INTO `voluntarios` VALUES ('222.222.222-22','Kah Linda','1221412123','eghngjmfgnfbg','fregrdfv'),('44214651864','Leonardo','1192344345','leo123@gmail.com','12344321'),('555.444.333-22','Ryan Mori','1143824355','ryan.yahoo@gmail.com','12345678'),('993.552.468-43','Adailton de Jesus','11978439038','adail.jesus@gmail.com','adailadail85');
/*!40000 ALTER TABLE `voluntarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-10 11:17:03
