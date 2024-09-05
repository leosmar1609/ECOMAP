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
INSERT INTO `administradores` VALUES (1,'leo.marcos6440@gmail.com','75869470Leo.','Leonardo Marcos');
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
INSERT INTO `funcionarios` VALUES ('22300988640','Marina Fernandes Costa','11974642115','mariafernandesc99@gmail.com','basculhant33',22),('32178004300','Pedro vilas boas lafaiette','11988675421','vilasboas46@gmail.com','Pedrinhogames46',15);
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
INSERT INTO `pontosdecoleta` VALUES (15,'Avenida dos sonhadores,4568','04553456','Vila encantada'),(22,'Avenida dos ventos, 3900','04538143','Itaim bibi');
UNLOCK TABLES;
DROP TABLE IF EXISTS `rastreamento`;
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
LOCK TABLES `rastreamento` WRITE;
INSERT INTO `rastreamento` VALUES (11,'Madeira','5 KG',22,11,'2024-09-02 09:28:28'),(13,'Resto de Alimentos','5 maÃ§as mordidas',15,11,'2024-09-02 09:28:28'),(13,'Resto de Alimentos','4 cascas',22,11,'2024-09-02 09:28:28'),(10,'Metal','4 placas',22,37,'2024-09-02 10:49:58'),(10,'Metal','4 placas',22,37,'2024-09-02 11:55:28'),(7,'Papel/papelÃ£o','4 caixas grandes',15,37,'2024-09-02 13:14:41');
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
INSERT INTO `residuos` VALUES (7,'Papel/papelÃ£o','ReciclÃ¡vel','Esses tipos de resÃ­duos podem ser jornais, livros, filtros de cafÃ©, embalagens de alimentos, etc.','Lixeiras designadas ao descarte de papel.'),(8,'PlÃ¡stico','ReciclÃ¡vel e Industrial','Esses tipos de resÃ­duos podem ser garrafas de garrafas de Ã¡gua, sacolas plÃ¡sticas, escovas de dente, etc.','Lixeiras designadas ao descarte de plÃ¡stico.'),(9,'Vidro','ReciclÃ¡vel e Industrial','Esses tipos de resÃ­duos podem ser garrafas de bebidas alcoÃ³licas, copos e taÃ§as, frascos de perfume, garrafas de azeite, etc.','Lixeiras designadas ao descarte de vidro.'),(10,'Metal','ReciclÃ¡vel e Industrial','Esses tipos de resÃ­duos podem ser panelas, talheres, pregos, vigas e colunas de aÃ§o, etc.','Lixeiras designadas ao descarte de metais.'),(11,'Madeira','ReciclÃ¡vel, ConstruÃ§Ã£o e DemoliÃ§Ã£o','Esses tipos de resÃ­duos podem ser mesas e cadeiras, armÃ¡rios e estantes, tÃ¡buas de corte, molduras de quadros, etc.','Lixeiras designadas ao descarte de madeira ou instalaÃ§Ãµes de reciclagem.'),(12,'Radioativo','Perigoso','Esses tipos de resÃ­duos devem ser manuseados cuidadosamente pois muitos deles sÃ£o pilhas, detectores de metais, equipamentos cientÃ­ficos, etc.','InstalaÃ§Ãµes de gerenciamento de resÃ­duos radioativos licenciadas e regulamentadas.'),(13,'Resto de Alimentos','OrgÃ¢nico','Esse tipo de resÃ­duos podem ser qualquer tipo de alimentos como cascas de frutas e restos de comida.','Podem ser descartadas em residÃªncias domiciliares ou em lixeiras designadas ao descarte desses restos de alimentos.'),(14,'AmbulatÃ³rio ou de serviÃ§os de saÃºde','ServiÃ§o de saÃºde','Esses tipos podem ser pinÃ§as, bisturis, equipamentos de sutura, luvas cirÃºrgicas, etc.','InstalaÃ§Ãµes de gerenciamento de resÃ­duos de serviÃ§os de saÃºde (RSS), como incineradores ou autoclaves.');
UNLOCK TABLES;
DROP TABLE IF EXISTS `voluntarios`;
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
LOCK TABLES `voluntarios` WRITE;
INSERT INTO `voluntarios` VALUES (6,_binary '13524678900','AurÃ©lio Correia','11222111111','auau@gmail.com',_binary 'auau123','ON'),(8,_binary '87654321000','Morgana Noxus','1196772345','','','OFF'),(9,_binary '98765432199','Samira Arcane','1197534676','','','OFF'),(10,_binary '32165489743','Leona LeBlanc','1197531100','','','OFF'),(11,_binary '97832167718','Reyna Phoenix','123123123','reynaphoenix@gmail.com',_binary 'reynap3nisis','ON'),(12,_binary '60475094341','Sova Brimstone Sarkov','1190042787','sovasarkov@gmail.com',_binary 'Brimpedra13','ON'),(13,_binary '51564689150','Kayo Deadlock','1195365630','deadlockkayo@gmail.com',_binary 'Mortotrancado22','ON'),(14,_binary '57896550976','Adalversio Mancinni','1196643470','adalversiomancinni@gmail.com',_binary 'Bolsomito2022','ON'),(16,_binary '67875021609','Maria Conceicao Hoffman','1195465755','mariahoffman@gmail.com',_binary '12345678A','ON'),(18,_binary '30989126800','Eder Camargo','1194757654','edercamargo@gmail.com',_binary 'jardimdoeder54','ON'),(33,_binary '33322213331','Mr Shadows','11999922299','','','OFF'),(34,_binary '99999999999','Sandra Barbosa','12555555555','','','OFF'),(35,_binary '#e}ôU(pòyW\Ç\ÂGh','Sergio Cortella','11911911911','sergio.cortella@gmail.com',_binary '5§p+Ûbr«>¯6J','ON'),(36,_binary 'eyB¤\Þ$M\â`l,','Aleatoriedade vento','11998899889','alea.alea@gmail.com',_binary 'û\Æ\Ä\Ù*\Î8©}Â²eò÷V','ON'),(37,_binary '21432231424','ulisses cavalcante','11991111199','ulisses.12@gmail.com',_binary 'û\Æ\Ä\Ù*\Î8©}Â²eò÷V','ON');
UNLOCK TABLES;
