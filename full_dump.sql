-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: na7ki
-- ------------------------------------------------------
-- Server version	8.0.46-0ubuntu0.24.04.3

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
-- Table structure for table `bug_report`
--

DROP TABLE IF EXISTS `bug_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bug_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `inquiry_content` varchar(255) NOT NULL,
  `inquirer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmy14d0l9ebs465s1oj4a31k41` (`inquirer_id`),
  CONSTRAINT `FKmy14d0l9ebs465s1oj4a31k41` FOREIGN KEY (`inquirer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_report`
--

LOCK TABLES `bug_report` WRITE;
/*!40000 ALTER TABLE `bug_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `bug_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cases`
--

DROP TABLE IF EXISTS `cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cases` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `child_name` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cases`
--

LOCK TABLES `cases` WRITE;
/*!40000 ALTER TABLE `cases` DISABLE KEYS */;
INSERT INTO `cases` VALUES (1,'Test Child',NULL);
/*!40000 ALTER TABLE `cases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Table structure for table `contact_request`
--

DROP TABLE IF EXISTS `contact_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `inquiry_content` varchar(255) NOT NULL,
  `inquirer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6pyhy3ysupsgs3bdwn670pmwu` (`inquirer_id`),
  CONSTRAINT `FK6pyhy3ysupsgs3bdwn670pmwu` FOREIGN KEY (`inquirer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_request`
--

LOCK TABLES `contact_request` WRITE;
/*!40000 ALTER TABLE `contact_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_items`
--

DROP TABLE IF EXISTS `exercise_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `item_key` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  `task_id` bigint NOT NULL,
  `image_id` bigint DEFAULT NULL,
  `sound_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbf0awtl7jddj7aw0vfj4xdoxv` (`task_id`),
  KEY `FKegpirg1ktvf47pxgdrh1c7i4h` (`image_id`),
  KEY `FKfw0dnt1m41uvgsr3ej2l94p2l` (`sound_id`),
  CONSTRAINT `FKbf0awtl7jddj7aw0vfj4xdoxv` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FKegpirg1ktvf47pxgdrh1c7i4h` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`),
  CONSTRAINT `FKfw0dnt1m41uvgsr3ej2l94p2l` FOREIGN KEY (`sound_id`) REFERENCES `sounds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_items`
--

LOCK TABLES `exercise_items` WRITE;
/*!40000 ALTER TABLE `exercise_items` DISABLE KEYS */;
INSERT INTO `exercise_items` VALUES (7,'intro',NULL,1,4,NULL,29),(8,'apple','تفاحة',2,4,200,25),(9,'banana','موزة',3,4,173,28),(10,'carrot','جزر',4,4,171,27),(11,'broccoli','بروكلي',5,4,199,26),(12,'grapes','عنب',6,4,196,26),(13,'intro',NULL,1,6,NULL,30),(14,'elephant','فيل',2,6,203,NULL),(15,'fox','ثعلب',3,6,205,NULL),(16,'cat','قطة',4,6,207,NULL),(17,'dog','كلب',5,6,206,NULL),(18,'penguin','بطريق',6,6,202,NULL),(19,'sheep','خروف',7,6,204,NULL),(20,'lion','أسد',8,6,167,NULL),(21,'monkey','قرد',9,6,175,NULL),(22,'option_clap',NULL,1,2,182,15),(23,'option_wave',NULL,2,2,183,12),(24,'option_jump',NULL,3,2,186,13),(25,'bear_clap',NULL,4,2,187,NULL),(26,'bear_wave',NULL,5,2,185,NULL),(27,'bear_jump',NULL,6,2,184,NULL),(28,'question_clap',NULL,7,2,NULL,14),(29,'question_wave',NULL,8,2,NULL,16),(30,'question_jump',NULL,9,2,NULL,16),(31,'intro',NULL,1,8,NULL,38),(32,'dog','كلب',2,8,206,32),(33,'cat','قطة',3,8,207,33),(34,'cow','بقرة',4,8,213,37),(35,'sheep','خروف',5,8,204,36),(36,'lion','أسد',6,8,167,35),(37,'duck','بطة',7,8,214,34),(38,'cat','القطة',1,5,207,40),(39,'carrot','الجزرة',2,5,171,44),(40,'apple','التفاحة',3,5,200,45),(41,'cow','البقرة',4,5,213,43),(42,'ball','الكرة',5,5,209,42),(43,'car','السيارة',6,5,219,41),(44,'scene_happy',NULL,1,3,188,18),(45,'scene_sad',NULL,2,3,195,20),(46,'scene_angry',NULL,3,3,193,23),(47,'scene_scared',NULL,4,3,194,17),(48,'face_happy','سعيد',5,3,168,NULL),(49,'face_sad','حزين',6,3,190,21),(50,'face_angry','غاضب',7,3,192,22),(51,'face_scared','خائف',8,3,191,19),(52,'rabbit','أرنب',1,9,168,NULL),(53,'carrot','جزرة',2,9,171,NULL),(54,'lion','أسد',3,9,167,NULL),(55,'meat','لحم',4,9,172,NULL),(56,'cow','بقرة',5,9,174,NULL),(57,'grass','عشب',6,9,170,NULL),(58,'monkey','قرد',7,9,175,NULL),(59,'banana','موزة',8,9,173,NULL),(60,'bird','طائر',9,9,169,NULL),(61,'worm','دودة',10,9,NULL,NULL),(62,'audio_1',NULL,11,9,NULL,3),(63,'audio_2',NULL,12,9,NULL,4),(64,'audio_3',NULL,13,9,NULL,1),(65,'audio_4',NULL,14,9,NULL,2),(66,'audio_5',NULL,15,9,NULL,NULL),(67,'intro',NULL,1,1,NULL,8),(68,'red','أحمر',2,1,176,7),(69,'green','أخضر',3,1,180,6),(70,'yellow','أصفر',4,1,179,11),(71,'blue','أزرق',5,1,178,5),(72,'white','أبيض',6,1,177,9),(73,'black','أسود',7,1,181,10),(74,'table','طاولة',1,7,210,NULL),(75,'ball','كرة',2,7,209,NULL),(76,'on','فوق',3,7,209,NULL),(77,'under','تحت',4,7,209,31),(78,'left','يسار',5,7,209,NULL),(79,'right','يمين',6,7,209,NULL),(80,'table','طاولة',1,7,210,NULL),(81,'ball','كرة',2,7,209,NULL),(82,'on','فوق',3,7,209,NULL),(83,'under','تحت',4,7,209,NULL),(84,'left','يسار',5,7,209,NULL),(85,'right','يمين',6,7,209,NULL),(86,'face_happy','سعيد',5,3,168,NULL);
/*!40000 ALTER TABLE `exercise_items` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'singular',NULL,'تفاحة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275729/singular/je4ee3ynephfdyiiyamt.jpg',NULL,NULL),(2,'singular',NULL,'موزة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275731/singular/i6j8bgo8hjomrndnuf9f.jpg',NULL,NULL),(3,'singular',NULL,'كتاب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275732/singular/jiobbipsj6u6bgl1scir.jpg',NULL,NULL),(4,'singular',NULL,'قلم.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275733/singular/vixug70p9p3t6ixe1syw.jpg',NULL,NULL),(5,'plural',NULL,'تفاح.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275734/plural/eg0islvmmh2q10fnevmj.jpg',NULL,NULL),(6,'plural',NULL,'موز.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275736/plural/vbygzwguwc4ihtrp8uth.jpg',NULL,NULL),(7,'plural',NULL,'اقلام.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275737/plural/nzarm4mqfote1ehibiry.jpg',NULL,NULL),(8,'plural',NULL,'كتب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275738/plural/pkvtilp52igwc2ylyyrh.jpg',NULL,NULL),(9,'female',NULL,'غزالة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584245/female/kamezysmwqvuglxeunol.png',NULL,NULL),(10,'female',NULL,'قطة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584246/female/rqnvy9fk7ciehtoxqzd2.png',NULL,NULL),(11,'female',NULL,'شجرة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584247/female/srsojblrcsvss29qvrxi.png',NULL,NULL),(12,'female',NULL,'بنت.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584248/female/kewppupwjhc5tn7kdmch.png',NULL,NULL),(13,'female',NULL,'أم.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584253/female/fvudiyuyrouk0jyamplt.png',NULL,NULL),(14,'female',NULL,'اميرة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584255/female/at1vkfrywpxhtnobisw6.jpg',NULL,NULL),(15,'female',NULL,'طاولة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584256/female/gxq4by3vfm94hhmaux0g.jpg',NULL,NULL),(16,'female',NULL,'كنبة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584257/female/erp8tgtfsqftnss9jq83.jpg',NULL,NULL),(17,'male',NULL,'حصان.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584261/male/u4gqlxid0jlimbiluojz.png',NULL,NULL),(18,'male',NULL,'كلب.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584263/male/kk5mu8rh2zjufappm3az.png',NULL,NULL),(19,'male',NULL,'أب.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584266/male/yo4qfnlglmhuamd9kn8y.png',NULL,NULL),(20,'male',NULL,'جرس.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584267/male/nvnz1oee5f4tlnr8dk4o.jpg',NULL,NULL),(21,'male',NULL,'كرسي.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584269/male/tha3f57vgtcghuq4fnnu.png',NULL,NULL),(22,'male',NULL,'امير.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584274/male/wawcp4n8wvjmu7ysixh6.png',NULL,NULL),(23,'male',NULL,'ولد.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584276/male/xaxo3ha3qe3fbyfnprrw.png',NULL,NULL),(24,'male',NULL,'باص.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584281/male/htunleybg6mqgrvectve.png',NULL,NULL),(157,'cover','png','feed_bear.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597751/cover/kv7whjklfmwgqzdgrbpb.png','cover/kv7whjklfmwgqzdgrbpb',952790),(158,'cover','png','where_is.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597758/cover/ruetzxi4kqbgwrr4odna.png','cover/ruetzxi4kqbgwrr4odna',2256442),(159,'cover','png','trace_path.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597763/cover/ma1tnuertv1w5ixrdcuf.png','cover/ma1tnuertv1w5ixrdcuf',1753896),(160,'cover','png','emotions.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597767/cover/j79jitriqhbhkdpfgghs.png','cover/j79jitriqhbhkdpfgghs',1640933),(161,'cover','png','cognition_package_cover.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597772/cover/g9or2paynfpjatse3r2p.png','cover/g9or2paynfpjatse3r2p',1293292),(162,'cover','png','color_sort.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597776/cover/pjeuhehnmzshv7uqfowo.png','cover/pjeuhehnmzshv7uqfowo',929524),(163,'cover','png','sound_match.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597780/cover/ysy59we6gpz6u8bjw585.png','cover/ysy59we6gpz6u8bjw585',1186056),(164,'cover','png','copy_animal.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597786/cover/nngqhxwchs0axrpbozvx.png','cover/nngqhxwchs0axrpbozvx',1840206),(165,'cover','png','match_alike.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597791/cover/gdz37spxoe89lo06xn90.png','cover/gdz37spxoe89lo06xn90',1155140),(166,'cover','png','put_object.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597795/cover/hacar27kkhb9q8524uby.png','cover/hacar27kkhb9q8524uby',1275082),(167,'trace_path','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597799/trace_path/wxpeoyc8kpz4mh4hvygm.png','trace_path/wxpeoyc8kpz4mh4hvygm',795379),(168,'trace_path','png','rabbit_happy.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597803/trace_path/r5y10iaddf5rdzd3gghm.png','trace_path/r5y10iaddf5rdzd3gghm',556678),(169,'trace_path','png','bird.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597806/trace_path/p3vckiq5dfsauq5hmqgr.png','trace_path/p3vckiq5dfsauq5hmqgr',623685),(170,'trace_path','png','grass.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597810/trace_path/vufr0nqrynayo85zojl8.png','trace_path/vufr0nqrynayo85zojl8',1031180),(171,'trace_path','png','carrot.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597812/trace_path/vinvdbawlnyazlzzco71.png','trace_path/vinvdbawlnyazlzzco71',211811),(172,'trace_path','png','meat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597815/trace_path/ced2gjbcl9lclcnqpjrz.png','trace_path/ced2gjbcl9lclcnqpjrz',387518),(173,'trace_path','png','banana.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597821/trace_path/t7ul4rcjpdsjz9ciil57.png','trace_path/t7ul4rcjpdsjz9ciil57',472139),(174,'trace_path','png','cow.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597825/trace_path/fhhou4weggfaf8nfilds.png','trace_path/fhhou4weggfaf8nfilds',723661),(175,'trace_path','png','monkey.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597833/trace_path/rkpyxsqkv8wfxbvtsaks.png','trace_path/rkpyxsqkv8wfxbvtsaks',665300),(176,'color_sort','png','balloon_red.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597835/color_sort/v1ltr6g57aaj4xvaljmr.png','color_sort/v1ltr6g57aaj4xvaljmr',134505),(177,'color_sort','png','balloon_white.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597836/color_sort/frmruxewerkgblguh9gd.png','color_sort/frmruxewerkgblguh9gd',137992),(178,'color_sort','png','balloon_blue.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597838/color_sort/ngaxjpngmppjweorii3s.png','color_sort/ngaxjpngmppjweorii3s',97109),(179,'color_sort','png','balloon_yellow.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597847/color_sort/mwmczlkqjnoh0earnor0.png','color_sort/mwmczlkqjnoh0earnor0',126075),(180,'color_sort','png','balloon_green.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597851/color_sort/w9n0zuzkrdqudpltg0ai.png','color_sort/w9n0zuzkrdqudpltg0ai',123249),(181,'color_sort','png','balloon_black.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597855/color_sort/sun1osuxoml3pzgactsl.png','color_sort/sun1osuxoml3pzgactsl',106861),(182,'copy_animal','png','kid_clap.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597860/copy_animal/zqdo8lssd9gjodydjsum.png','copy_animal/zqdo8lssd9gjodydjsum',573474),(183,'copy_animal','png','kid_wave.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597863/copy_animal/evkomja23zsqvuzl65kb.png','copy_animal/evkomja23zsqvuzl65kb',569257),(184,'copy_animal','png','jump.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597871/copy_animal/uxxsqkdnivr2rwqdmwov.png','copy_animal/uxxsqkdnivr2rwqdmwov',736110),(185,'copy_animal','png','wave.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597875/copy_animal/hxnfvethilmrjzm48ilv.png','copy_animal/hxnfvethilmrjzm48ilv',1033594),(186,'copy_animal','png','kid_jump.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597881/copy_animal/ofbsa8ypbuwihs9nksqr.png','copy_animal/ofbsa8ypbuwihs9nksqr',604957),(187,'copy_animal','png','clap.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597886/copy_animal/nkhn17de1i2kg3m2g1ae.png','copy_animal/nkhn17de1i2kg3m2g1ae',883756),(188,'emotions','png','rabbit_happy_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597889/emotions/xoz0n0f8qxjgt9mldn6w.png','emotions/xoz0n0f8qxjgt9mldn6w',789339),(189,'emotions','png','rabbit_happy.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597894/emotions/gp0yzdrcsxocplzgjvhn.png','emotions/gp0yzdrcsxocplzgjvhn',556678),(190,'emotions','png','rabbit_sad.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597900/emotions/f10gazuispbzofaxpoap.png','emotions/f10gazuispbzofaxpoap',557427),(191,'emotions','png','rabbit_scared.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597903/emotions/hsjtglu7pxikpzzotdiq.png','emotions/hsjtglu7pxikpzzotdiq',560790),(192,'emotions','png','rabbit_angry.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597905/emotions/dveksb1vqibr5o34ctrz.png','emotions/dveksb1vqibr5o34ctrz',601245),(193,'emotions','png','rabbit_angry_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597909/emotions/qhd1rjwq7hfofqqnjmnq.png','emotions/qhd1rjwq7hfofqqnjmnq',1287751),(194,'emotions','png','rabbit_scared_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597918/emotions/mdrjnsd8wwq5s7w4cooc.png','emotions/mdrjnsd8wwq5s7w4cooc',1182779),(195,'emotions','png','rabbit_sad_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597922/emotions/pz0sgcnsfvvodrctawa9.png','emotions/pz0sgcnsfvvodrctawa9',519362),(196,'feed_bear','png','grapes.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597926/feed_bear/znmhdixol7uo0afnxmic.png','feed_bear/znmhdixol7uo0afnxmic',791907),(197,'feed_bear','png','carrot.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597928/feed_bear/msrvbehrfo944ckl8keb.png','feed_bear/msrvbehrfo944ckl8keb',211811),(198,'feed_bear','png','banana.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597930/feed_bear/muehpguqv7z0lkaysjq3.png','feed_bear/muehpguqv7z0lkaysjq3',472139),(199,'feed_bear','png','broccoli.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597933/feed_bear/z3fa0hoy5kmuhe74jihp.png','feed_bear/z3fa0hoy5kmuhe74jihp',862200),(200,'feed_bear','png','apple.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597938/feed_bear/z2ezt4h3xgoosbrcaex9.png','feed_bear/z2ezt4h3xgoosbrcaex9',499303),(201,'match_alike','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597948/match_alike/sear3dntyadzvixuhbjb.png','match_alike/sear3dntyadzvixuhbjb',795379),(202,'match_alike','png','penguin.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597950/match_alike/isb4b17uh8zkwdrtjhij.png','match_alike/isb4b17uh8zkwdrtjhij',465749),(203,'match_alike','png','elephant.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597952/match_alike/he8z3jct9piytdkdsuby.png','match_alike/he8z3jct9piytdkdsuby',717307),(204,'match_alike','png','sheep.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597955/match_alike/k4cqookascxongddkptn.png','match_alike/k4cqookascxongddkptn',506079),(205,'match_alike','png','fox.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597958/match_alike/nbrwevcybiewyrtqykzi.png','match_alike/nbrwevcybiewyrtqykzi',708834),(206,'match_alike','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597962/match_alike/gwhxc1bum1ae2nv3sfax.png','match_alike/gwhxc1bum1ae2nv3sfax',603008),(207,'match_alike','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597964/match_alike/bspfgr2tn1vogimnu88t.png','match_alike/bspfgr2tn1vogimnu88t',970723),(208,'match_alike','png','monkey.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597966/match_alike/qozhswsvxdqsnz89wamv.png','match_alike/qozhswsvxdqsnz89wamv',665300),(209,'put_object','png','ball.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597969/put_object/zx2nlu5jt01bhwmhotvb.png','put_object/zx2nlu5jt01bhwmhotvb',484041),(210,'put_object','png','table.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597973/put_object/esxv55onzicygs93sbuk.png','put_object/esxv55onzicygs93sbuk',778350),(211,'sound_match','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597976/sound_match/sa3ew5loyaaagpuovm23.png','sound_match/sa3ew5loyaaagpuovm23',795379),(212,'sound_match','png','sheep.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597990/sound_match/hmgzphu3xukducqxsmu1.png','sound_match/hmgzphu3xukducqxsmu1',506079),(213,'sound_match','png','cow2.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597994/sound_match/r9k4wpfeaxul4gmzyd7y.png','sound_match/r9k4wpfeaxul4gmzyd7y',813458),(214,'sound_match','png','duck.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597997/sound_match/nnz0tzjb0pciqwenixkg.png','sound_match/nnz0tzjb0pciqwenixkg',490482),(215,'sound_match','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598000/sound_match/jjof98iaexegvd8tchx5.png','sound_match/jjof98iaexegvd8tchx5',603008),(216,'sound_match','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598005/sound_match/plu8u7xqinbmm5ttlb0d.png','sound_match/plu8u7xqinbmm5ttlb0d',970723),(217,'where_is','png','ball.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598011/where_is/vqmctscfllx9eu9sit69.png','where_is/vqmctscfllx9eu9sit69',484041),(218,'where_is','png','apple.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598013/where_is/be6qiubkthur2rekcffl.png','where_is/be6qiubkthur2rekcffl',499303),(219,'where_is','png','car.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598017/where_is/c9av4dhtnqxq8yfondv9.png','where_is/c9av4dhtnqxq8yfondv9',857553),(220,'where_is','png','cow2.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598023/where_is/uob6tctfmrkixv94gxoi.png','where_is/uob6tctfmrkixv94gxoi',813458),(221,'where_is','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598028/where_is/nxg8xfdldgduj5uklcoy.png','where_is/nxg8xfdldgduj5uklcoy',603008),(222,'where_is','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598032/where_is/iqkujozhkc9euit1szdi.png','where_is/iqkujozhkc9euit1szdi',970723),(223,'missing','png','feed_bear.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668035/missing/nvk5iouhf9kkkiuxz2l5.png','missing/nvk5iouhf9kkkiuxz2l5',952790),(224,'missing','png','where_is.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668041/missing/kboisw7jisdmi6f31xpk.png','missing/kboisw7jisdmi6f31xpk',2256442),(225,'missing','png','trace_path.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668045/missing/saeytjyh5udrnkakbkvw.png','missing/saeytjyh5udrnkakbkvw',1753896),(226,'missing','png','emotions.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668049/missing/mgyu4atuzv2p5dsjgv0r.png','missing/mgyu4atuzv2p5dsjgv0r',1640933),(227,'missing','png','cognition_package_cover.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668056/missing/ocbst107o90afuguhjsk.png','missing/ocbst107o90afuguhjsk',1293292),(228,'missing','png','color_sort.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668058/missing/r3hh6ff0t4x4kmmqj5nf.png','missing/r3hh6ff0t4x4kmmqj5nf',929524),(229,'missing','png','sound_match.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668062/missing/ypjubqocebtrco61sj2q.png','missing/ypjubqocebtrco61sj2q',1186056),(230,'missing','png','copy_animal.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668067/missing/fbssvqdqyct6yvwx9n5w.png','missing/fbssvqdqyct6yvwx9n5w',1840206),(231,'missing','png','match_alike.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668072/missing/na6yrw6gdfdzdaom8qbe.png','missing/na6yrw6gdfdzdaom8qbe',1155140),(232,'missing','png','put_object.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668076/missing/rxhwjitk8zagsuxy2x9o.png','missing/rxhwjitk8zagsuxy2x9o',1275082);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (1,'تعرّف على المفرد والجمع','مفرد و جمع',1),(2,'تعرّف على المذكر والمؤنث','مذكر ومؤنث',18),(3,'تمارين معرفية تساعد الطفل على بناء المهارات الأساسية اللازمة لتطوير الكلام والتواصل، مثل الانتباه، والتركيز، وتقليد الآخرين، واللعب الرمزي.','تمارين الادراك',161);
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
  CONSTRAINT `FKbhxnsr0osyqj98qqcexec5edv` FOREIGN KEY (`id`) REFERENCES `user` (`id`),
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
-- Table structure for table `sounds`
--

DROP TABLE IF EXISTS `sounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sounds` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `folder_name` varchar(255) NOT NULL,
  `format` varchar(255) DEFAULT NULL,
  `public_id` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `sound_name` varchar(255) NOT NULL,
  `sound_url` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgr74re5g7380qc0itjmj8q36w` (`public_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sounds`
--

LOCK TABLES `sounds` WRITE;
/*!40000 ALTER TABLE `sounds` DISABLE KEYS */;
INSERT INTO `sounds` VALUES (1,'trace_path','mp3','trace_path/fiuzxsex6f24gzobxtzz',70988,'cow_grass.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597818/trace_path/fiuzxsex6f24gzobxtzz.mp3'),(2,'trace_path','mp3','trace_path/jpbebquygrcygooorevn',68898,'monkey_banana.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597819/trace_path/jpbebquygrcygooorevn.mp3'),(3,'trace_path','mp3','trace_path/mugba3effdrrep2iwtng',71824,'rabbit_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597827/trace_path/mugba3effdrrep2iwtng.mp3'),(4,'trace_path','mp3','trace_path/wxudqccztlhgpukyxwcb',69316,'lion_meat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597829/trace_path/wxudqccztlhgpukyxwcb.mp3'),(5,'color_sort','mp3','color_sort/xv6rchn6dlvncqkvjozk',51762,'blue.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597839/color_sort/xv6rchn6dlvncqkvjozk.mp3'),(6,'color_sort','mp3','color_sort/bc4i1xhlqwjmfvzfhakp',53016,'green.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597841/color_sort/bc4i1xhlqwjmfvzfhakp.mp3'),(7,'color_sort','mp3','color_sort/ovc8xh3fnf2mgwuf14vm',49254,'red.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597843/color_sort/ovc8xh3fnf2mgwuf14vm.mp3'),(8,'color_sort','mp3','color_sort/wxklvwpdhwmit0bbndsy',66391,'match_balloon.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597844/color_sort/wxklvwpdhwmit0bbndsy.mp3'),(9,'color_sort','mp3','color_sort/vu5yrtpyntseezdphpjw',51762,'white.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597849/color_sort/vu5yrtpyntseezdphpjw.mp3'),(10,'color_sort','mp3','color_sort/miher47yoevj22aiw2kh',51762,'black.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597852/color_sort/miher47yoevj22aiw2kh.mp3'),(11,'color_sort','mp3','color_sort/vrjbzfurdpj8y4jd8snl',49254,'yellow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597853/color_sort/vrjbzfurdpj8y4jd8snl.mp3'),(12,'copy_animal','mp3','copy_animal/xvepryltbuyl9lci1nev',79347,'bear_wave.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597857/copy_animal/xvepryltbuyl9lci1nev.mp3'),(13,'copy_animal','mp3','copy_animal/tpv4rhkrllvhqq7gup1l',75586,'bear_jump.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597858/copy_animal/tpv4rhkrllvhqq7gup1l.mp3'),(14,'copy_animal','mp3','copy_animal/blshp8vbirlhy5xit4eu',57613,'q1_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597861/copy_animal/blshp8vbirlhy5xit4eu.mp3'),(15,'copy_animal','mp3','copy_animal/edku35wrb7ob6wrsopdg',67226,'bear_clap.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597865/copy_animal/edku35wrb7ob6wrsopdg.mp3'),(16,'copy_animal','mp3','copy_animal/gc48yb8111jfobqntrfb',76839,'q2_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597877/copy_animal/gc48yb8111jfobqntrfb.mp3'),(17,'emotions','mp3','emotions/ufwxmogg0gniyeoa0xih',73914,'rabbit_scene_scared.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597895/emotions/ufwxmogg0gniyeoa0xih.mp3'),(18,'emotions','mp3','emotions/zi8bna5ywbi3mu3cayr0',75586,'rabbit_scene_happy.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597896/emotions/zi8bna5ywbi3mu3cayr0.mp3'),(19,'emotions','mp3','emotions/c0i9xytrwhzuxbf9wrmg',50926,'scared.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597901/emotions/c0i9xytrwhzuxbf9wrmg.mp3'),(20,'emotions','mp3','emotions/vdzytdxguovuxtlvt5vt',75586,'rabbit_scene_sad.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597911/emotions/vdzytdxguovuxtlvt5vt.mp3'),(21,'emotions','mp3','emotions/bpu3j3rxjey3tkodbj1n',50090,'sad.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597913/emotions/bpu3j3rxjey3tkodbj1n.mp3'),(22,'emotions','mp3','emotions/piaiikqw95tiv5aifp1a',49254,'angry.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597915/emotions/piaiikqw95tiv5aifp1a.mp3'),(23,'emotions','mp3','emotions/i36npivtrivo0rf8z1xg',77675,'rabbit_scene_angry.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597919/emotions/i36npivtrivo0rf8z1xg.mp3'),(24,'emotions','mp3','emotions/so7piphwy86h2mmkhae6',71824,'rabbit_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597923/emotions/so7piphwy86h2mmkhae6.mp3'),(25,'feed_bear','mp3','feed_bear/fcswlhnipgkye39892lf',96237,'crunch_apple.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597935/feed_bear/fcswlhnipgkye39892lf.mp3'),(26,'feed_bear','mp3','feed_bear/w6tu6k4pildlq825lvmb',48960,'crunch.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597940/feed_bear/w6tu6k4pildlq825lvmb.mp3'),(27,'feed_bear','mp3','feed_bear/qfh8cku4xyrvxdia2rc5',209815,'crunch_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597942/feed_bear/qfh8cku4xyrvxdia2rc5.mp3'),(28,'feed_bear','mp3','feed_bear/qujvv8yzicgehuc552yp',64845,'crunch_banana.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597943/feed_bear/qujvv8yzicgehuc552yp.mp3'),(29,'feed_bear','mp3','feed_bear/xgpfhfsk6w0fptiqmnof',62629,'hungry_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597945/feed_bear/xgpfhfsk6w0fptiqmnof.mp3'),(30,'match_alike','mp3','match_alike/sfl5qoaf2aeylxz2u5ox',80601,'choose_alike.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597959/match_alike/sfl5qoaf2aeylxz2u5ox.mp3'),(31,'put_object','mp3','put_object/bz2nkiwskh1a1kmnxxiv',66391,'put_the_ball_under_table.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597970/put_object/bz2nkiwskh1a1kmnxxiv.mp3'),(32,'sound_match','mp3','sound_match/rjvyraca0n75uo6vhcom',40124,'dog.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597977/sound_match/rjvyraca0n75uo6vhcom.mp3'),(33,'sound_match','mp3','sound_match/zgvbz7wepcwb2wk7vyeu',39168,'cat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597979/sound_match/zgvbz7wepcwb2wk7vyeu.mp3'),(34,'sound_match','mp3','sound_match/mrbmkzfp2urilnnrktcc',36000,'duck.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597980/sound_match/mrbmkzfp2urilnnrktcc.mp3'),(35,'sound_match','mp3','sound_match/ikjm5uppxzsbrexlyjxe',131239,'lion.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597982/sound_match/ikjm5uppxzsbrexlyjxe.mp3'),(36,'sound_match','mp3','sound_match/qjjrqybp2hsrfw4mho4y',20898,'sheep.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597984/sound_match/qjjrqybp2hsrfw4mho4y.mp3'),(37,'sound_match','mp3','sound_match/ggly1zh9okdx4x2rrvh9',42240,'cow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597985/sound_match/ggly1zh9okdx4x2rrvh9.mp3'),(38,'sound_match','mp3','sound_match/xoprfrvvtedt0xjzbkvo',109440,'listen_choose.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597987/sound_match/xoprfrvvtedt0xjzbkvo.mp3'),(39,'where_is','mp3','where_is/ppcq3rvvenejkmjidtqk',55524,'where_is_the_dog.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598006/where_is/ppcq3rvvenejkmjidtqk.mp3'),(40,'where_is','mp3','where_is/hbdmvt7ldpxpeyxdgdjj',52598,'where_is_the_cat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598008/where_is/hbdmvt7ldpxpeyxdgdjj.mp3'),(41,'where_is','mp3','where_is/ngebfziwzxbdy5ntsnfe',56777,'where_is_the_car.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598018/where_is/ngebfziwzxbdy5ntsnfe.mp3'),(42,'where_is','mp3','where_is/lfvq9os8zjkrvdeceixy',52598,'where_is_the_ball.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598020/where_is/lfvq9os8zjkrvdeceixy.mp3'),(43,'where_is','mp3','where_is/epyyetec8pkshsz12zxa',53016,'where_is_the_cow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598024/where_is/epyyetec8pkshsz12zxa.mp3'),(44,'where_is','mp3','where_is/edsabzmib63zvawmob46',55524,'where_is_the_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598033/where_is/edsabzmib63zvawmob46.mp3'),(45,'where_is','mp3','where_is/ecxfvk5prve0juxjuaa4',54688,'where_is_the_apple.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598035/where_is/ecxfvk5prve0juxjuaa4.mp3'),(46,'put_object','mp3','put_object/y2hfy2byd1eyf3j3by8p',68898,'put_the_ball_in_left.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668110/put_object/y2hfy2byd1eyf3j3by8p.mp3'),(47,'put_object','mp3','put_object/eif5mxvtsbidlv94yytl',68898,'put_the_ball_on_top.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668112/put_object/eif5mxvtsbidlv94yytl.mp3'),(48,'put_object','mp3','put_object/vnulqtlui9010y7tzx1h',50926,'happy.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668113/put_object/vnulqtlui9010y7tzx1h.mp3'),(49,'put_object','mp3','put_object/fxapl3uvnlww2ue3ws0z',66391,'put_the_ball_under_table.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668115/put_object/fxapl3uvnlww2ue3ws0z.mp3'),(50,'put_object','mp3','put_object/x44pkgi4ifhlfoxifjja',67226,'put_the_ball_in_right.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668116/put_object/x44pkgi4ifhlfoxifjja.mp3');
/*!40000 ALTER TABLE `sounds` ENABLE KEYS */;
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
  CONSTRAINT `FKd35lt0t0kt5i2179gl5pkg5vj` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist`
--

LOCK TABLES `specialist` WRITE;
/*!40000 ALTER TABLE `specialist` DISABLE KEYS */;
INSERT INTO `specialist` VALUES ('haram','2005-01-27','SP1',4),('Haram','2005-01-27','SP2',5);
/*!40000 ALTER TABLE `specialist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialist-personal_image`
--

DROP TABLE IF EXISTS `specialist-personal_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialist-personal_image` (
  `user_id` bigint NOT NULL,
  `personal_image_path` varchar(100) DEFAULT NULL,
  KEY `FKjwtbkhl05m8lsaaka85fhu42d` (`user_id`),
  CONSTRAINT `FKjwtbkhl05m8lsaaka85fhu42d` FOREIGN KEY (`user_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist-personal_image`
--

LOCK TABLES `specialist-personal_image` WRITE;
/*!40000 ALTER TABLE `specialist-personal_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialist-personal_image` ENABLE KEYS */;
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
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  `task_key` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `package_id` bigint NOT NULL,
  `image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhvssn5g17tcsicd74yodxa923` (`task_key`),
  KEY `FK253uljohrial0xx801jvm9a5e` (`package_id`),
  KEY `FKcs6gp5uudahnxcl32hb7t0kgv` (`image_id`),
  CONSTRAINT `FK253uljohrial0xx801jvm9a5e` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `FKcs6gp5uudahnxcl32hb7t0kgv` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'التطابق والتصنيف','تمرين يقوم فيه الطفل بسحب البالونات الملونة إلى البقع اللونية المطابقة لها',NULL,1,'color_sort','تمرين مطابقة الألوان',3,162),(2,'التقليد والاستجابة','يظهر دب يقوم بحركات مختلفة (تصفيق، تلويح، قفز) ويطلب من اختيار ما يفعله الدب وتقليده.',NULL,2,'copy_animal','تمرين التقليد ',3,164),(3,'الذكاء العاطفي والتعرف على المشاعر','يُعرض مشهد لأرنب يعبر عن شعور معين (سعيد، حزين، غاضب، خائف) ويطلب من الطفل تحديد الشعور الصحيح',NULL,3,'emotions','تمرين التعرف على المشاعر',3,160),(4,'صيغة الطلب والتواصل','تمرين يطلب الدب من الطفل اطعامه ويقوم الطفل بسحب أنواع مختلفة من الطعام إلى فم الدب لإطعامه ',NULL,4,'feed_bear','تمرين إطعام الدب',3,157),(5,'التمييز البصري والانتباه',' يُطلب من الطفل العثور على صورة معينة من بين مجموعة من الصور المختلفة',NULL,5,'where_is','تمرين أين ...؟',3,158),(6,'الذاكرة والتطابق','تمرين ذاكرة بصري حيث يجب على الطفل العثور على الأزواج المتشابهة من البطاقات',NULL,6,'match_alike','تمرين مطابقة المتشابهات',3,165),(7,'المفاهيم المكانية والإدراك الحسي','يُطلب من الطفل وضع كرة في مكان معين بالنسبة للطاولة (فوق، تحت، يسار، يمين)',NULL,7,'put_object','تمرين وضع الكرة ',3,166),(8,'التمييز السمعي والربط بين الصوت والصورة',' يتم تشغيل صوت معين ويطلب من الطفل اختيار الصورة الصحيحة',NULL,8,'sound_match','تمرين مطابقةالأصوات',3,163),(9,'المهارات الحركية الدقيقة والتنسيق بين العين واليد',' يظهر مسار متقطع بين صورتين وعلى الطفل تتبع المسار بإصبعه من البداية إلى النهاية',NULL,9,'trace_path','تمرين تتبع المسار',3,159);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `role` varchar(31) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` tinyint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `display-image_path` varchar(100) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `email_verification_time` datetime(6) DEFAULT NULL,
  `gender` enum('FEMALE','MALE') NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `phone_number` varchar(13) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK4bgmpi98dylab6qdvf9xyaxu4` (`phone_number`),
  CONSTRAINT `user_chk_1` CHECK ((`role` in (_utf8mb4'PATIENT',_utf8mb4'SPECIALIST')))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('SPECIALIST',4,21,'2026-06-27 01:52:42.687000',NULL,'maih5413@gmail.com',NULL,'FEMALE',_binary '\0','maihassan','$2a$10$fXQxY3JcEplaLXM09pLcXextFFsASEvF1NmBkEzlSSzKNvN.efQFC','01140954022','2026-06-27 01:52:42.687000'),('SPECIALIST',5,21,'2026-06-27 01:53:44.122000',NULL,'mai.hassan.20260627@example.com',NULL,'FEMALE',_binary '\0','Mai Hassan','$2a$10$BQqvEgM1RGCU0rbYs6sBjuoKcidrga1TIO/U2cbfz/1azQTYjkm.q','01140954023','2026-06-27 01:53:44.122000');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
-- Table structure for table `verification_code`
--

DROP TABLE IF EXISTS `verification_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expires_at` datetime(6) NOT NULL,
  `four_digit_code` varchar(4) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn576esytmxxfkgon3ja83h5vp` (`user_id`),
  CONSTRAINT `FKgy5dhio3a6c9me7s0x9v1y4d2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_code`
--

LOCK TABLES `verification_code` WRITE;
/*!40000 ALTER TABLE `verification_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_code` ENABLE KEYS */;
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

-- Dump completed on 2026-06-28 20:41:20
