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
INSERT INTO `funcionarios` VALUES ('22300988640','Marina Fernandes Costa','11974642115','mariafernandesc99@gmail.com','basculhant33',22),('32178004300','Pedro vilas boas lafaiette','11988675421','vilasboas46@gmail.com','Pedrinhogames46',15);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontosdecoleta`
--

LOCK TABLES `pontosdecoleta` WRITE;
/*!40000 ALTER TABLE `pontosdecoleta` DISABLE KEYS */;
INSERT INTO `pontosdecoleta` VALUES (15,'Avenida dos sonhadores,4568','04553456','Vila encantada'),(22,'Avenida dos ventos, 3900','04538143','Itaim bibi');
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
  `tiporesiduo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `quantcoletada` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `codpontocoleta` int NOT NULL,
  `codvol` int NOT NULL,
  `datahora_coleta` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `codresiduo` (`codresiduo`),
  KEY `codpontocoleta` (`codpontocoleta`),
  KEY `fk_codvol` (`codvol`),
  CONSTRAINT `fk_codvol` FOREIGN KEY (`codvol`) REFERENCES `voluntarios` (`codvol`),
  CONSTRAINT `rastreamento_ibfk_1` FOREIGN KEY (`codresiduo`) REFERENCES `residuos` (`codresiduo`),
  CONSTRAINT `rastreamento_ibfk_2` FOREIGN KEY (`codpontocoleta`) REFERENCES `pontosdecoleta` (`codpontocoleta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rastreamento`
--

LOCK TABLES `rastreamento` WRITE;
/*!40000 ALTER TABLE `rastreamento` DISABLE KEYS */;
INSERT INTO `rastreamento` VALUES (11,'Madeira','5 KG',22,11,'2024-09-02 09:28:28'),(13,'Resto de Alimentos','5 maças mordidas',15,11,'2024-09-02 09:28:28'),(13,'Resto de Alimentos','4 cascas',22,11,'2024-09-02 09:28:28'),(10,'Metal','4 placas',22,37,'2024-09-02 10:49:58'),(10,'Metal','4 placas',22,37,'2024-09-02 11:55:28'),(7,'Papel/papelão','4 caixas grandes',15,37,'2024-09-02 13:14:41');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `residuos`
--

LOCK TABLES `residuos` WRITE;
/*!40000 ALTER TABLE `residuos` DISABLE KEYS */;
INSERT INTO `residuos` VALUES (7,'Papel/papelão','Reciclável','Esses tipos de resíduos podem ser jornais, livros, filtros de café, embalagens de alimentos, etc.','Lixeiras designadas ao descarte de papel.'),(8,'Plástico','Reciclável e Industrial','Esses tipos de resíduos podem ser garrafas de garrafas de água, sacolas plásticas, escovas de dente, etc.','Lixeiras designadas ao descarte de plástico.'),(9,'Vidro','Reciclável e Industrial','Esses tipos de resíduos podem ser garrafas de bebidas alcoólicas, copos e taças, frascos de perfume, garrafas de azeite, etc.','Lixeiras designadas ao descarte de vidro.'),(10,'Metal','Reciclável e Industrial','Esses tipos de resíduos podem ser panelas, talheres, pregos, vigas e colunas de aço, etc.','Lixeiras designadas ao descarte de metais.'),(11,'Madeira','Reciclável, Construção e Demolição','Esses tipos de resíduos podem ser mesas e cadeiras, armários e estantes, tábuas de corte, molduras de quadros, etc.','Lixeiras designadas ao descarte de madeira ou instalações de reciclagem.'),(12,'Radioativo','Perigoso','Esses tipos de resíduos devem ser manuseados cuidadosamente pois muitos deles são pilhas, detectores de metais, equipamentos científicos, etc.','Instalações de gerenciamento de resíduos radioativos licenciadas e regulamentadas.'),(13,'Resto de Alimentos','Orgânico','Esse tipo de resíduos podem ser qualquer tipo de alimentos como cascas de frutas e restos de comida.','Podem ser descartadas em residências domiciliares ou em lixeiras designadas ao descarte desses restos de alimentos.'),(14,'Ambulatório ou de serviços de saúde','Serviço de saúde','Esses tipos podem ser pinças, bisturis, equipamentos de sutura, luvas cirúrgicas, etc.','Instalações de gerenciamento de resíduos de serviços de saúde (RSS), como incineradores ou autoclaves.');
/*!40000 ALTER TABLE `residuos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voluntarios`
--

DROP TABLE IF EXISTS `voluntarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voluntarios` (
  `codvol` int NOT NULL AUTO_INCREMENT,
  `CPFVOL` varbinary(255) DEFAULT NULL,
  `nomevol` varchar(40) NOT NULL,
  `fonevol` varchar(14) NOT NULL,
  `emailvol` varchar(30) NOT NULL,
  `SENHAVOL` varbinary(255) DEFAULT NULL,
  `estadovol` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codvol`),
  UNIQUE KEY `cpfvol` (`CPFVOL`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voluntarios`
--

LOCK TABLES `voluntarios` WRITE;
/*!40000 ALTER TABLE `voluntarios` DISABLE KEYS */;
INSERT INTO `voluntarios` VALUES (6,_binary '13524678900','Aurélio Correia','11222111111','auau@gmail.com',_binary 'auau123','ON'),(8,_binary '87654321000','Morgana Noxus','1196772345','','','OFF'),(9,_binary '98765432199','Samira Arcane','1197534676','','','OFF'),(10,_binary '32165489743','Leona LeBlanc','1197531100','','','OFF'),(11,_binary '97832167718','Reyna Phoenix','123123123','reynaphoenix@gmail.com',_binary 'reynap3nisis','ON'),(12,_binary '60475094341','Sova Brimstone Sarkov','1190042787','sovasarkov@gmail.com',_binary 'Brimpedra13','ON'),(13,_binary '51564689150','Kayo Deadlock','1195365630','deadlockkayo@gmail.com',_binary 'Mortotrancado22','ON'),(14,_binary '57896550976','Adalversio Mancinni','1196643470','adalversiomancinni@gmail.com',_binary 'Bolsomito2022','ON'),(16,_binary '67875021609','Maria Conceicao Hoffman','1195465755','mariahoffman@gmail.com',_binary '12345678A','ON'),(18,_binary '30989126800','Eder Camargo','1194757654','edercamargo@gmail.com',_binary 'jardimdoeder54','ON'),(33,_binary '33322213331','Mr Shadows','11999922299','','','OFF'),(34,_binary '99999999999','Sandra Barbosa','12555555555','','','OFF'),(35,_binary '#�e}�U(p�yW\�\�Gh','Sergio Cortella','11911911911','sergio.cortella@gmail.com',_binary '5�p+ۀbr�>�6�J�','ON'),(36,_binary '�e�yB�\�$M\�`l,','Aleatoriedade vento','11998899889','alea.alea@gmail.com',_binary '�\�\�\�*\�8�}²e��V','ON'),(37,_binary '21432231424','ulisses cavalcante','11991111199','ulisses.12@gmail.com',_binary '�\�\�\�*\�8�}²e��V','ON');
/*!40000 ALTER TABLE `voluntarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ecobanco'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-05 11:10:21
