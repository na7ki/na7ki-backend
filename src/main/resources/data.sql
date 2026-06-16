-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: na7ki
-- ------------------------------------------------------
-- Server version	8.0.46-0ubuntu0.24.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--


--
-- Table structure for table `choices`
--

DROP TABLE IF EXISTS `choices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choices` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  `question_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb4ek5sbd7hoppigypqtml4hvm` (`image_id`),
  KEY `FK4vhssp102sjhbey1y4rhiiyos` (`question_id`),
  CONSTRAINT `FK4vhssp102sjhbey1y4rhiiyos` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKb4ek5sbd7hoppigypqtml4hvm` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choices`
--

LOCK TABLES `choices` WRITE;
/*!40000 ALTER TABLE `choices` DISABLE KEYS */;
INSERT INTO `choices` VALUES (1,'SINGULAR',1,1),(2,'PLURAL',5,1),(3,'PLURAL',5,2),(4,'SINGULAR',1,2),(5,'SINGULAR',3,3),(6,'PLURAL',7,3),(7,'PLURAL',8,4),(8,'SINGULAR',4,4);
/*!40000 ALTER TABLE `choices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `folder_name` varchar(255) NOT NULL,
  `format` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) NOT NULL,
  `image_url` varchar(1000) NOT NULL,
  `public_id` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoif4v99e97wnnbkdbw54vdqvj` (`public_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'singular',NULL,'تفاحة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275729/singular/je4ee3ynephfdyiiyamt.jpg',NULL,NULL),(2,'singular',NULL,'موزة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275731/singular/i6j8bgo8hjomrndnuf9f.jpg',NULL,NULL),(3,'singular',NULL,'كتاب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275732/singular/jiobbipsj6u6bgl1scir.jpg',NULL,NULL),(4,'singular',NULL,'قلم.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275733/singular/vixug70p9p3t6ixe1syw.jpg',NULL,NULL),(5,'plural',NULL,'تفاح.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275734/plural/eg0islvmmh2q10fnevmj.jpg',NULL,NULL),(6,'plural',NULL,'موز.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275736/plural/vbygzwguwc4ihtrp8uth.jpg',NULL,NULL),(7,'plural',NULL,'اقلام.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275737/plural/nzarm4mqfote1ehibiry.jpg',NULL,NULL),(8,'plural',NULL,'كتب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275738/plural/pkvtilp52igwc2ylyyrh.jpg',NULL,NULL);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_images`
--

DROP TABLE IF EXISTS `package_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package_images` (
  `package_id` bigint NOT NULL,
  `image_id` bigint NOT NULL,
  KEY `FKcbk0upk1lv8acrww36r0hiesu` (`image_id`),
  KEY `FKckjgwbelio5r7ktswj44vbuxb` (`package_id`),
  CONSTRAINT `FKcbk0upk1lv8acrww36r0hiesu` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`),
  CONSTRAINT `FKckjgwbelio5r7ktswj44vbuxb` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_images`
--

LOCK TABLES `package_images` WRITE;
/*!40000 ALTER TABLE `package_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `cover_image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKisc0pvihkewgp2tp0omxkk9vm` (`cover_image_id`),
  CONSTRAINT `FKisc0pvihkewgp2tp0omxkk9vm` FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (1,'تعرّف على المفرد والجمع','مفرد و جمع',1);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `primary_diagnosis` varchar(300) NOT NULL,
  `notes` varchar(1000) NOT NULL,
  `has_previous_treatment` bit(1) NOT NULL,
  `no_siblings` smallint NOT NULL,
  `order_in_siblings` smallint NOT NULL,
  `school_name` varchar(70) NOT NULL,
  `scholastic_level` varchar(30) NOT NULL,
  `body_parts_identification_score` smallint NOT NULL,
  `fruits_identification_score` smallint NOT NULL,
  `objects_identification_score` smallint NOT NULL,
  `expressiveness` smallint NOT NULL,
  `receptiveness` smallint NOT NULL,
  `internal_language_score` smallint NOT NULL,
  `pitch` enum('HIGH','LOW','NORMAL') NOT NULL,
  `intensity` enum('HIGH','LOW','NORMAL') NOT NULL,
  `quality` enum('BREATHY','HOARSE','MUFFLED','PURE') NOT NULL,
  `dysphonia_degree` enum('ONE','THREE','TWO','ZERO') NOT NULL,
  `speech_speed` enum('FAST','NORMAL','SLOW') NOT NULL,
  `speech_fluency` enum('INTERRUPTED','NORMAL','STUTTERED') NOT NULL,
  `speech_vibration` enum('NASAL','NORMAL','ORAL') NOT NULL,
  `speech_clarity` enum('HIGH','LOW','MEDIUM') NOT NULL,
  `patient_id` varchar(15) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5k7l7wk9ogyt1ag6vku4a4lwo` (`patient_id`),
  CONSTRAINT `FKf0or75ex3abs31ottuqg8s301` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  `question_text` varchar(255) DEFAULT NULL,
  `package_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe7og4n49t9oc5dkqd9bgtfof6` (`package_id`),
  CONSTRAINT `FKe7og4n49t9oc5dkqd9bgtfof6` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'SINGULAR',1,'اختر الصيغة المفردة',1),(2,'PLURAL',2,'اختر الصيغة الجمع',1),(3,'SINGULAR',3,'أي من هذه مفرد؟',1),(4,'PLURAL',4,'أي من هذه جمع؟',1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialist`
--

DROP TABLE IF EXISTS `specialist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialist` (
  `address` varchar(300) NOT NULL,
  `date_of_birth` date NOT NULL,
  `specialist_id` varchar(15) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK34mr056f0dytcik6sdsj6nye` (`specialist_id`),
  CONSTRAINT `FKh3w6mud9ch7yau9tqr100r0vr` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist`
--

LOCK TABLES `specialist` WRITE;
/*!40000 ALTER TABLE `specialist` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialist-personal_images`
--

DROP TABLE IF EXISTS `specialist-personal_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialist-personal_images` (
  `user_id` bigint NOT NULL,
  `personal_image_path` varchar(100) NOT NULL,
  KEY `FK5j0uketxs6vajt9g6y28wx3a5` (`user_id`),
  CONSTRAINT `FK5j0uketxs6vajt9g6y28wx3a5` FOREIGN KEY (`user_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist-personal_images`
--

LOCK TABLES `specialist-personal_images` WRITE;
/*!40000 ALTER TABLE `specialist-personal_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialist-personal_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_answers`
--

DROP TABLE IF EXISTS `user_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `selected_type` varchar(255) DEFAULT NULL,
  `question_id` bigint NOT NULL,
  `session_id` bigint NOT NULL,
  `choice_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6b46l4bb7a6wfxvmn6l7ig8vo` (`question_id`),
  KEY `FKc9xia559o9or61rfbl7gaeoxi` (`session_id`),
  KEY `FKajx4pyjrsow6r8gudsjp5uuji` (`choice_id`),
  CONSTRAINT `FK6b46l4bb7a6wfxvmn6l7ig8vo` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKajx4pyjrsow6r8gudsjp5uuji` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`id`),
  CONSTRAINT `FKc9xia559o9or61rfbl7gaeoxi` FOREIGN KEY (`session_id`) REFERENCES `user_sessions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_answers`
--

LOCK TABLES `user_answers` WRITE;
/*!40000 ALTER TABLE `user_answers` DISABLE KEYS */;
INSERT INTO `user_answers` VALUES (1,_binary '',NULL,1,1,1),(2,_binary '',NULL,2,1,3),(3,_binary '',NULL,3,1,5),(4,_binary '',NULL,4,1,7),(5,_binary '\0',NULL,1,2,2),(6,_binary '',NULL,2,2,3),(7,_binary '',NULL,3,2,5),(8,_binary '',NULL,4,2,7),(9,_binary '',NULL,1,3,1),(10,_binary '',NULL,2,3,3),(11,_binary '',NULL,3,3,5),(12,_binary '',NULL,4,3,7),(13,_binary '',NULL,1,4,1),(14,_binary '',NULL,2,4,3),(15,_binary '',NULL,3,4,5),(16,_binary '',NULL,4,4,7);
/*!40000 ALTER TABLE `user_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_sessions`
--

DROP TABLE IF EXISTS `user_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `score` int NOT NULL,
  `total_questions` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `package_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKth7eglwuuqyqqliawc6slfcax` (`package_id`),
  CONSTRAINT `FKth7eglwuuqyqqliawc6slfcax` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_sessions`
--

LOCK TABLES `user_sessions` WRITE;
/*!40000 ALTER TABLE `user_sessions` DISABLE KEYS */;
INSERT INTO `user_sessions` VALUES (1,'2026-05-09 01:09:39.336645',4,4,123,1),(2,'2026-05-09 01:09:56.821116',3,4,123,1),(3,'2026-05-09 01:13:08.828652',4,4,123,1),(4,'2026-06-16 21:34:36.840396',4,4,123,1);
/*!40000 ALTER TABLE `user_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `role` varchar(31) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` tinyint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `display-image_path` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `email_verification_time` datetime(6) DEFAULT NULL,
  `gender` enum('FEMALE','MALE') NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `phone_number` varchar(13) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK9q63snka3mdh91as4io72espi` (`phone_number`),
  CONSTRAINT `users_chk_1` CHECK ((`role` in (_utf8mb4'PATIENT',_utf8mb4'SPECIALIST')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_codes`
--

DROP TABLE IF EXISTS `verification_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expires_at` datetime(6) NOT NULL,
  `four_digit_code` varchar(4) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbf0ofef2q09iwv2jg00aygy4q` (`user_id`),
  CONSTRAINT `FKa4qo6nts1xd94owirq5evcpda` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_codes`
--

LOCK TABLES `verification_codes` WRITE;
/*!40000 ALTER TABLE `verification_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-16 23:23:06
