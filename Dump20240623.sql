CREATE DATABASE  IF NOT EXISTS `ecobanco`;
USE `ecobanco`;
DROP TABLE IF EXISTS `administradores`;
CREATE TABLE `administradores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `nome` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `administradores` WRITE;
INSERT INTO `administradores` VALUES (1,'teste@gmail.com','admin','Admin Admin');
UNLOCK TABLES;
DROP TABLE IF EXISTS `funcionarios`;
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
LOCK TABLES `funcionarios` WRITE;
INSERT INTO `funcionarios` VALUES ('12332499543','Carolina Pinto Bianco','11903225667','biancocarolina@gmail.com','pintocarolina69',17),('22300988640','Marina Fernandes Costa','11974642115','mariafernandesc99@gmail.com','basculhant33',22),('32178004300','Pedro vilas boas lafaiette','11988675421','vilasboas46@gmail.com','Pedrinhogames46',15);
UNLOCK TABLES;
DROP TABLE IF EXISTS `pontosdecoleta`;
CREATE TABLE `pontosdecoleta` (
  `codpontocoleta` int NOT NULL AUTO_INCREMENT,
  `endpontocoleta` varchar(50) NOT NULL,
  `ceppontocoleta` varchar(9) NOT NULL,
  `bairropontocoleta` varchar(20) NOT NULL,
  PRIMARY KEY (`codpontocoleta`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `pontosdecoleta` WRITE;
INSERT INTO `pontosdecoleta` VALUES (15,'Avenida dos sonhadores,4568','04553456','Vila encantada'),(17,'Avenida dos pioneiros, 1578','01310784','Horizonte azul'),(22,'Avenida dos ventos, 3900','04538143','Itaim bibi');
UNLOCK TABLES;
DROP TABLE IF EXISTS `rastreamento`;
CREATE TABLE `rastreamento` (
  `codresiduo` int NOT NULL,
  `tiporesiduo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `quantcoletada` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `codpontocoleta` int NOT NULL,
  `codvol` int NOT NULL,
  KEY `codresiduo` (`codresiduo`),
  KEY `codpontocoleta` (`codpontocoleta`),
  KEY `fk_codvol` (`codvol`),
  CONSTRAINT `fk_codvol` FOREIGN KEY (`codvol`) REFERENCES `voluntarios` (`codvol`),
  CONSTRAINT `rastreamento_ibfk_1` FOREIGN KEY (`codresiduo`) REFERENCES `residuos` (`codresiduo`),
  CONSTRAINT `rastreamento_ibfk_2` FOREIGN KEY (`codpontocoleta`) REFERENCES `pontosdecoleta` (`codpontocoleta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `rastreamento` WRITE;
UNLOCK TABLES;
DROP TABLE IF EXISTS `residuos`;
CREATE TABLE `residuos` (
  `codresiduo` int NOT NULL AUTO_INCREMENT,
  `tiporesiduo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `categoria` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descricao` varchar(300) NOT NULL,
  `descarte` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`codresiduo`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `residuos` WRITE;
INSERT INTO `residuos` VALUES (7,'Papel/papelão','Reciclável','Esses tipos de resíduos podem ser jornais, livros, filtros de café, embalagens de alimentos, etc.','Lixeiras designadas ao descarte de papel.'),(8,'Plástico','Reciclável e Industrial','Esses tipos de resíduos podem ser garrafas de garrafas de água, sacolas plásticas, escovas de dente, etc.','Lixeiras designadas ao descarte de plástico.'),(9,'Vidro','Reciclável e Industrial','Esses tipos de resíduos podem ser garrafas de bebidas alcoólicas, copos e taças, frascos de perfume, garrafas de azeite, etc.','Lixeiras designadas ao descarte de vidro.'),(10,'Metal','Reciclável e Industrial','Esses tipos de resíduos podem ser panelas, talheres, pregos, vigas e colunas de aço, etc.','Lixeiras designadas ao descarte de metais.'),(11,'Madeira','Reciclável, Construção e Demolição','Esses tipos de resíduos podem ser mesas e cadeiras, armários e estantes, tábuas de corte, molduras de quadros, etc.','Lixeiras designadas ao descarte de madeira ou instalações de reciclagem.'),(12,'Radioativo','Perigoso','Esses tipos de resíduos devem ser manuseados cuidadosamente pois muitos deles são pilhas, detectores de metais, equipamentos científicos, etc.','Instalações de gerenciamento de resíduos radioativos licenciadas e regulamentadas.'),(13,'Resto de Alimentos','Orgânico','Esse tipo de resíduos podem ser qualquer tipo de alimentos como cascas de frutas e restos de comida.','Podem ser descartadas em residências domiciliares ou em lixeiras designadas ao descarte desses restos de alimentos.'),(14,'Ambulatório ou de serviços de saúde','Serviço de saúde','Esses tipos podem ser pinças, bisturis, equipamentos de sutura, luvas cirúrgicas, etc.','Instalações de gerenciamento de resíduos de serviços de saúde (RSS), como incineradores ou autoclaves.');
UNLOCK TABLES;
DROP TABLE IF EXISTS `voluntarios`;
CREATE TABLE `voluntarios` (
  `codvol` int NOT NULL AUTO_INCREMENT,
  `cpfvol` varchar(14) NOT NULL,
  `nomevol` varchar(40) NOT NULL,
  `fonevol` varchar(14) NOT NULL,
  `emailvol` varchar(30) NOT NULL,
  `senhavol` varchar(15) NOT NULL,
  `estadovol` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`codvol`),
  UNIQUE KEY `cpfvol` (`cpfvol`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `voluntarios` WRITE;
INSERT INTO `voluntarios` VALUES (6,'13524678900','Irelia Heimerdinger','1190337533','','','OFF'),(8,'87654321000','Morgana Noxus','1196772345','','','OFF'),(9,'98765432199','Samira Arcane','1197534676','','','OFF'),(10,'32165489743','Leona LeBlanc','1197531100','','','OFF'),(11,'97832167718','Reyna Phoenix','1198768653','reynaphoenix@gmail.com','reynap3nisis','ON'),(12,'60475094341','Sova Brimstone Sarkov','1190042787','sovasarkov@gmail.com','Brimpedra13','ON'),(13,'51564689150','Kayo Deadlock','1195365630','deadlockkayo@gmail.com','Mortotrancado22','ON'),(14,'57896550976','Adalversio Mancinni','1196643470','adalversiomancinni@gmail.com','Bolsomito2022','ON'),(16,'67875021609','Maria Conceicao Hoffman','1195465755','mariahoffman@gmail.com','12345678A','ON'),(17,'44214631838','Mirna Domingues Grego','1194497443','mirnadominguesg440@gmail.com','Mirnagg69','ON'),(18,'30989126800','Eder Camargo','1194757654','edercamargo@gmail.com','jardimdoeder54','ON'),(27,'20103275678','leoeleo','75757575','leoleoleoll@gmail.com','1234','ON'),(28,'33322233322','Leozinho Teste','11994608499','','','OFF'),(29,'11332211221','Leozin da zo','11111111111','zoleo@gmail.com','123454321','ON'),(30,'10000000000','sexooooooooooooooooooooooooo','11993347488','rainaldo.souza@gmail.com','12345','ON');
UNLOCK TABLES;
