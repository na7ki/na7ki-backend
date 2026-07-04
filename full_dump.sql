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
-- Dumping data for table `assigned_exercise`
--
LOCK TABLES `assigned_exercise` WRITE;
/*!40000 ALTER TABLE `assigned_exercise` DISABLE KEYS */;
INSERT INTO `assigned_exercise` VALUES (1, 'QUESTION', 1, 1, NULL), (2, 'QUESTION', 2, 2, NULL), (3, 'TASK', 1, NULL, 1), (4, 'TASK', 2, NULL, 2);
/*!40000 ALTER TABLE `assigned_exercise` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `assignment`
--
LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (1, CURRENT_TIMESTAMP, 3, 1), (2, CURRENT_TIMESTAMP, 4, 1);
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bug_report`
--
LOCK TABLES `bug_report` WRITE;
/*!40000 ALTER TABLE `bug_report` DISABLE KEYS */;
INSERT INTO `bug_report` VALUES (1, CURRENT_TIMESTAMP, 'bug report 1', 1), (2, CURRENT_TIMESTAMP, 'bug report 2', 2);
/*!40000 ALTER TABLE `bug_report` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `categories`
--
LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `choices`
--
LOCK TABLES `choices` WRITE;
/*!40000 ALTER TABLE `choices` DISABLE KEYS */;
INSERT INTO `choices` VALUES
(1,'SINGULAR',1,1), (2,'PLURAL',5,1),
(3,'PLURAL',5,2), (4,'SINGULAR',1,2),
(5,'SINGULAR',3,3), (6,'PLURAL',7,3),
(7,'PLURAL',8,4), (8,'SINGULAR',4,4),

(9,'MALE',19,5), (10,'FEMALE',13,5),
(11,'FEMALE',13,6), (12,'MALE',19,6),
(13,'MALE',22,7), (14,'FEMALE',14,7),
(15,'FEMALE',14,8), (16,'MALE',22,8),
(17,'MALE',24,9), (18,'FEMALE',11,9),
(19,'FEMALE',11,10), (20,'MALE',24,10),
(21,'MALE',20,11), (22,'FEMALE',15,11),
(23,'FEMALE',15,12), (24,'MALE',20,12),
(25,'MALE',17,13), (26,'FEMALE',9,13),
(27,'FEMALE',9,14), (28,'MALE',17,14),
(29,'MALE',21,15), (30,'FEMALE',16,15),
(31,'FEMALE',16,16), (32,'MALE',21,16),
(33,'MALE',18,17), (34,'FEMALE',10,17),
(35,'FEMALE',10,18), (36,'MALE',18,18),
(37,'MALE',23,19), (38,'FEMALE',12,19),
(39,'FEMALE',12,20), (40,'MALE',23,20);
/*!40000 ALTER TABLE `choices` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `contact_request`
--
LOCK TABLES `contact_request` WRITE;
/*!40000 ALTER TABLE `contact_request` DISABLE KEYS */;
INSERT INTO `contact_request` VALUES (1, CURRENT_TIMESTAMP, 'contact request 1', 3), (2, CURRENT_TIMESTAMP, 'contact request 2', 4);
/*!40000 ALTER TABLE `contact_request` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `exercise_items`
--
LOCK TABLES `exercise_items` WRITE;
/*!40000 ALTER TABLE `exercise_items` DISABLE KEYS */;
INSERT INTO `exercise_items` VALUES (7,'intro',NULL,1,4,NULL,29),(8,'apple','تفاحة',2,4,200,25),(9,'banana','موزة',3,4,173,28),(10,'carrot','جزر',4,4,171,27),(11,'broccoli','بروكلي',5,4,199,26),(12,'grapes','عنب',6,4,196,26),(13,'intro',NULL,1,6,NULL,30),(14,'elephant','فيل',2,6,203,NULL),(15,'fox','ثعلب',3,6,205,NULL),(16,'cat','قطة',4,6,207,NULL),(17,'dog','كلب',5,6,206,NULL),(18,'penguin','بطريق',6,6,202,NULL),(19,'sheep','خروف',7,6,204,NULL),(20,'lion','أسد',8,6,167,NULL),(21,'monkey','قرد',9,6,175,NULL),(22,'option_clap',NULL,1,2,182,15),(23,'option_wave',NULL,2,2,183,12),(24,'option_jump',NULL,3,2,186,13),(25,'bear_clap',NULL,4,2,187,NULL),(26,'bear_wave',NULL,5,2,185,NULL),(27,'bear_jump',NULL,6,2,184,NULL),(28,'question_clap',NULL,7,2,NULL,14),(29,'question_wave',NULL,8,2,NULL,16),(30,'question_jump',NULL,9,2,NULL,16),(31,'intro',NULL,1,8,NULL,38),(32,'dog','كلب',2,8,206,32),(33,'cat','قطة',3,8,207,33),(34,'cow','بقرة',4,8,213,37),(35,'sheep','خروف',5,8,204,36),(36,'lion','أسد',6,8,167,35),(37,'duck','بطة',7,8,214,34),(38,'cat','القطة',1,5,207,40),(39,'carrot','الجزرة',2,5,171,44),(40,'apple','التفاحة',3,5,200,45),(41,'cow','البقرة',4,5,213,43),(42,'ball','الكرة',5,5,209,42),(43,'car','السيارة',6,5,219,41),(44,'scene_happy',NULL,1,3,188,18),(45,'scene_sad',NULL,2,3,195,20),(46,'scene_angry',NULL,3,3,193,23),(47,'scene_scared',NULL,4,3,194,17),(48,'face_happy','سعيد',5,3,168,NULL),(49,'face_sad','حزين',6,3,190,21),(50,'face_angry','غاضب',7,3,192,22),(51,'face_scared','خائف',8,3,191,19),(52,'rabbit','أرنب',1,9,168,NULL),(53,'carrot','جزرة',2,9,171,NULL),(54,'lion','أسد',3,9,167,NULL),(55,'meat','لحم',4,9,172,NULL),(56,'cow','بقرة',5,9,174,NULL),(57,'grass','عشب',6,9,170,NULL),(58,'monkey','قرد',7,9,175,NULL),(59,'banana','موزة',8,9,173,NULL),(60,'bird','طائر',9,9,169,NULL),(61,'worm','دودة',10,9,NULL,NULL),(62,'audio_1',NULL,11,9,NULL,3),(63,'audio_2',NULL,12,9,NULL,4),(64,'audio_3',NULL,13,9,NULL,1),(65,'audio_4',NULL,14,9,NULL,2),(66,'audio_5',NULL,15,9,NULL,NULL),(67,'intro',NULL,1,1,NULL,8),(68,'red','أحمر',2,1,176,7),(69,'green','أخضر',3,1,180,6),(70,'yellow','أصفر',4,1,179,11),(71,'blue','أزرق',5,1,178,5),(72,'white','أبيض',6,1,177,9),(73,'black','أسود',7,1,181,10),(74,'table','طاولة',1,7,210,NULL),(75,'ball','كرة',2,7,209,NULL),(76,'on','فوق',3,7,209,NULL),(77,'under','تحت',4,7,209,31),(78,'left','يسار',5,7,209,NULL),(79,'right','يمين',6,7,209,NULL),(80,'table','طاولة',1,7,210,NULL),(81,'ball','كرة',2,7,209,NULL),(82,'on','فوق',3,7,209,NULL),(83,'under','تحت',4,7,209,NULL),(84,'left','يسار',5,7,209,NULL),(85,'right','يمين',6,7,209,NULL),(86,'face_happy','سعيد',5,3,168,NULL);
/*!40000 ALTER TABLE `exercise_items` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `images`
--
LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'singular',NULL,'تفاحة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275729/singular/je4ee3ynephfdyiiyamt.jpg',NULL,NULL),(2,'singular',NULL,'موزة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275731/singular/i6j8bgo8hjomrndnuf9f.jpg',NULL,NULL),(3,'singular',NULL,'كتاب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275732/singular/jiobbipsj6u6bgl1scir.jpg',NULL,NULL),(4,'singular',NULL,'قلم.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275733/singular/vixug70p9p3t6ixe1syw.jpg',NULL,NULL),(5,'plural',NULL,'تفاح.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275734/plural/eg0islvmmh2q10fnevmj.jpg',NULL,NULL),(6,'plural',NULL,'موز.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275736/plural/vbygzwguwc4ihtrp8uth.jpg',NULL,NULL),(7,'plural',NULL,'اقلام.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275737/plural/nzarm4mqfote1ehibiry.jpg',NULL,NULL),(8,'plural',NULL,'كتب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275738/plural/pkvtilp52igwc2ylyyrh.jpg',NULL,NULL),(9,'female',NULL,'غزالة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584245/female/kamezysmwqvuglxeunol.png',NULL,NULL),(10,'female',NULL,'قطة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584246/female/rqnvy9fk7ciehtoxqzd2.png',NULL,NULL),(11,'female',NULL,'شجرة.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584247/female/srsojblrcsvss29qvrxi.png',NULL,NULL),(12,'female',NULL,'بنت.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584248/female/kewppupwjhc5tn7kdmch.png',NULL,NULL),(13,'female',NULL,'أم.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584253/female/fvudiyuyrouk0jyamplt.png',NULL,NULL),(14,'female',NULL,'اميرة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584255/female/at1vkfrywpxhtnobisw6.jpg',NULL,NULL),(15,'female',NULL,'طاولة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584256/female/gxq4by3vfm94hhmaux0g.jpg',NULL,NULL),(16,'female',NULL,'كنبة.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584257/female/erp8tgtfsqftnss9jq83.jpg',NULL,NULL),(17,'male',NULL,'حصان.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584261/male/u4gqlxid0jlimbiluojz.png',NULL,NULL),(18,'male',NULL,'كلب.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584263/male/kk5mu8rh2zjufappm3az.png',NULL,NULL),(19,'male',NULL,'أب.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584266/male/yo4qfnlglmhuamd9kn8y.png',NULL,NULL),(20,'male',NULL,'جرس.jpg','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584267/male/nvnz1oee5f4tlnr8dk4o.jpg',NULL,NULL),(21,'male',NULL,'كرسي.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584269/male/tha3f57vgtcghuq4fnnu.png',NULL,NULL),(22,'male',NULL,'امير.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584274/male/wawcp4n8wvjmu7ysixh6.png',NULL,NULL),(23,'male',NULL,'ولد.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584276/male/xaxo3ha3qe3fbyfnprrw.png',NULL,NULL),(24,'male',NULL,'باص.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782584281/male/htunleybg6mqgrvectve.png',NULL,NULL),(157,'cover','png','feed_bear.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597751/cover/kv7whjklfmwgqzdgrbpb.png','cover/kv7whjklfmwgqzdgrbpb',952790),(158,'cover','png','where_is.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597758/cover/ruetzxi4kqbgwrr4odna.png','cover/ruetzxi4kqbgwrr4odna',2256442),(159,'cover','png','trace_path.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597763/cover/ma1tnuertv1w5ixrdcuf.png','cover/ma1tnuertv1w5ixrdcuf',1753896),(160,'cover','png','emotions.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597767/cover/j79jitriqhbhkdpfgghs.png','cover/j79jitriqhbhkdpfgghs',1640933),(161,'cover','png','cognition_package_cover.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597772/cover/g9or2paynfpjatse3r2p.png','cover/g9or2paynfpjatse3r2p',1293292),(162,'cover','png','color_sort.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597776/cover/pjeuhehnmzshv7uqfowo.png','cover/pjeuhehnmzshv7uqfowo',929524),(163,'cover','png','sound_match.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597780/cover/ysy59we6gpz6u8bjw585.png','cover/ysy59we6gpz6u8bjw585',1186056),(164,'cover','png','copy_animal.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597786/cover/nngqhxwchs0axrpbozvx.png','cover/nngqhxwchs0axrpbozvx',1840206),(165,'cover','png','match_alike.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597791/cover/gdz37spxoe89lo06xn90.png','cover/gdz37spxoe89lo06xn90',1155140),(166,'cover','png','put_object.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597795/cover/hacar27kkhb9q8524uby.png','cover/hacar27kkhb9q8524uby',1275082),(167,'trace_path','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597799/trace_path/wxpeoyc8kpz4mh4hvygm.png','trace_path/wxpeoyc8kpz4mh4hvygm',795379),(168,'trace_path','png','rabbit_happy.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597803/trace_path/r5y10iaddf5rdzd3gghm.png','trace_path/r5y10iaddf5rdzd3gghm',556678),(169,'trace_path','png','bird.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597806/trace_path/p3vckiq5dfsauq5hmqgr.png','trace_path/p3vckiq5dfsauq5hmqgr',623685),(170,'trace_path','png','grass.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597810/trace_path/vufr0nqrynayo85zojl8.png','trace_path/vufr0nqrynayo85zojl8',1031180),(171,'trace_path','png','carrot.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597812/trace_path/vinvdbawlnyazlzzco71.png','trace_path/vinvdbawlnyazlzzco71',211811),(172,'trace_path','png','meat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597815/trace_path/ced2gjbcl9lclcnqpjrz.png','trace_path/ced2gjbcl9lclcnqpjrz',387518),(173,'trace_path','png','banana.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597821/trace_path/t7ul4rcjpdsjz9ciil57.png','trace_path/t7ul4rcjpdsjz9ciil57',472139),(174,'trace_path','png','cow.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597825/trace_path/fhhou4weggfaf8nfilds.png','trace_path/fhhou4weggfaf8nfilds',723661),(175,'trace_path','png','monkey.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597833/trace_path/rkpyxsqkv8wfxbvtsaks.png','trace_path/rkpyxsqkv8wfxbvtsaks',665300),(176,'color_sort','png','balloon_red.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597835/color_sort/v1ltr6g57aaj4xvaljmr.png','color_sort/v1ltr6g57aaj4xvaljmr',134505),(177,'color_sort','png','balloon_white.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597836/color_sort/frmruxewerkgblguh9gd.png','color_sort/frmruxewerkgblguh9gd',137992),(178,'color_sort','png','balloon_blue.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597838/color_sort/ngaxjpngmppjweorii3s.png','color_sort/ngaxjpngmppjweorii3s',97109),(179,'color_sort','png','balloon_yellow.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597847/color_sort/mwmczlkqjnoh0earnor0.png','color_sort/mwmczlkqjnoh0earnor0',126075),(180,'color_sort','png','balloon_green.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597851/color_sort/w9n0zuzkrdqudpltg0ai.png','color_sort/w9n0zuzkrdqudpltg0ai',123249),(181,'color_sort','png','balloon_black.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597855/color_sort/sun1osuxoml3pzgactsl.png','color_sort/sun1osuxoml3pzgactsl',106861),(182,'copy_animal','png','kid_clap.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597860/copy_animal/zqdo8lssd9gjodydjsum.png','copy_animal/zqdo8lssd9gjodydjsum',573474),(183,'copy_animal','png','kid_wave.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597863/copy_animal/evkomja23zsqvuzl65kb.png','copy_animal/evkomja23zsqvuzl65kb',569257),(184,'copy_animal','png','jump.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597871/copy_animal/uxxsqkdnivr2rwqdmwov.png','copy_animal/uxxsqkdnivr2rwqdmwov',736110),(185,'copy_animal','png','wave.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597875/copy_animal/hxnfvethilmrjzm48ilv.png','copy_animal/hxnfvethilmrjzm48ilv',1033594),(186,'copy_animal','png','kid_jump.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597881/copy_animal/ofbsa8ypbuwihs9nksqr.png','copy_animal/ofbsa8ypbuwihs9nksqr',604957),(187,'copy_animal','png','clap.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597886/copy_animal/nkhn17de1i2kg3m2g1ae.png','copy_animal/nkhn17de1i2kg3m2g1ae',883756),(188,'emotions','png','rabbit_happy_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597889/emotions/xoz0n0f8qxjgt9mldn6w.png','emotions/xoz0n0f8qxjgt9mldn6w',789339),(189,'emotions','png','rabbit_happy.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597894/emotions/gp0yzdrcsxocplzgjvhn.png','emotions/gp0yzdrcsxocplzgjvhn',556678),(190,'emotions','png','rabbit_sad.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597900/emotions/f10gazuispbzofaxpoap.png','emotions/f10gazuispbzofaxpoap',557427),(191,'emotions','png','rabbit_scared.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597903/emotions/hsjtglu7pxikpzzotdiq.png','emotions/hsjtglu7pxikpzzotdiq',560790),(192,'emotions','png','rabbit_angry.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597905/emotions/dveksb1vqibr5o34ctrz.png','emotions/dveksb1vqibr5o34ctrz',601245),(193,'emotions','png','rabbit_angry_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597909/emotions/qhd1rjwq7hfofqqnjmnq.png','emotions/qhd1rjwq7hfofqqnjmnq',1287751),(194,'emotions','png','rabbit_scared_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597918/emotions/mdrjnsd8wwq5s7w4cooc.png','emotions/mdrjnsd8wwq5s7w4cooc',1182779),(195,'emotions','png','rabbit_sad_scene.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597922/emotions/pz0sgcnsfvvodrctawa9.png','emotions/pz0sgcnsfvvodrctawa9',519362),(196,'feed_bear','png','grapes.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597926/feed_bear/znmhdixol7uo0afnxmic.png','feed_bear/znmhdixol7uo0afnxmic',791907),(197,'feed_bear','png','carrot.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597928/feed_bear/msrvbehrfo944ckl8keb.png','feed_bear/msrvbehrfo944ckl8keb',211811),(198,'feed_bear','png','banana.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597930/feed_bear/muehpguqv7z0lkaysjq3.png','feed_bear/muehpguqv7z0lkaysjq3',472139),(199,'feed_bear','png','broccoli.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597933/feed_bear/z3fa0hoy5kmuhe74jihp.png','feed_bear/z3fa0hoy5kmuhe74jihp',862200),(200,'feed_bear','png','apple.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597938/feed_bear/z2ezt4h3xgoosbrcaex9.png','feed_bear/z2ezt4h3xgoosbrcaex9',499303),(201,'match_alike','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597948/match_alike/sear3dntyadzvixuhbjb.png','match_alike/sear3dntyadzvixuhbjb',795379),(202,'match_alike','png','penguin.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597950/match_alike/isb4b17uh8zkwdrtjhij.png','match_alike/isb4b17uh8zkwdrtjhij',465749),(203,'match_alike','png','elephant.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597952/match_alike/he8z3jct9piytdkdsuby.png','match_alike/he8z3jct9piytdkdsuby',717307),(204,'match_alike','png','sheep.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597955/match_alike/k4cqookascxongddkptn.png','match_alike/k4cqookascxongddkptn',506079),(205,'match_alike','png','fox.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597958/match_alike/nbrwevcybiewyrtqykzi.png','match_alike/nbrwevcybiewyrtqykzi',708834),(206,'match_alike','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597962/match_alike/gwhxc1bum1ae2nv3sfax.png','match_alike/gwhxc1bum1ae2nv3sfax',603008),(207,'match_alike','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597964/match_alike/bspfgr2tn1vogimnu88t.png','match_alike/bspfgr2tn1vogimnu88t',970723),(208,'match_alike','png','monkey.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597966/match_alike/qozhswsvxdqsnz89wamv.png','match_alike/qozhswsvxdqsnz89wamv',665300),(209,'put_object','png','ball.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597969/put_object/zx2nlu5jt01bhwmhotvb.png','put_object/zx2nlu5jt01bhwmhotvb',484041),(210,'put_object','png','table.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597973/put_object/esxv55onzicygs93sbuk.png','put_object/esxv55onzicygs93sbuk',778350),(211,'sound_match','png','lion.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597976/sound_match/sa3ew5loyaaagpuovm23.png','sound_match/sa3ew5loyaaagpuovm23',795379),(212,'sound_match','png','sheep.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597990/sound_match/hmgzphu3xukducqxsmu1.png','sound_match/hmgzphu3xukducqxsmu1',506079),(213,'sound_match','png','cow2.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597994/sound_match/r9k4wpfeaxul4gmzyd7y.png','sound_match/r9k4wpfeaxul4gmzyd7y',813458),(214,'sound_match','png','duck.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782597997/sound_match/nnz0tzjb0pciqwenixkg.png','sound_match/nnz0tzjb0pciqwenixkg',490482),(215,'sound_match','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598000/sound_match/jjof98iaexegvd8tchx5.png','sound_match/jjof98iaexegvd8tchx5',603008),(216,'sound_match','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598005/sound_match/plu8u7xqinbmm5ttlb0d.png','sound_match/plu8u7xqinbmm5ttlb0d',970723),(217,'where_is','png','ball.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598011/where_is/vqmctscfllx9eu9sit69.png','where_is/vqmctscfllx9eu9sit69',484041),(218,'where_is','png','apple.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598013/where_is/be6qiubkthur2rekcffl.png','where_is/be6qiubkthur2rekcffl',499303),(219,'where_is','png','car.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598017/where_is/c9av4dhtnqxq8yfondv9.png','where_is/c9av4dhtnqxq8yfondv9',857553),(220,'where_is','png','cow2.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598023/where_is/uob6tctfmrkixv94gxoi.png','where_is/uob6tctfmrkixv94gxoi',813458),(221,'where_is','png','dog.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598028/where_is/nxg8xfdldgduj5uklcoy.png','where_is/nxg8xfdldgduj5uklcoy',603008),(222,'where_is','png','cat.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782598032/where_is/iqkujozhkc9euit1szdi.png','where_is/iqkujozhkc9euit1szdi',970723),(223,'missing','png','feed_bear.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668035/missing/nvk5iouhf9kkkiuxz2l5.png','missing/nvk5iouhf9kkkiuxz2l5',952790),(224,'missing','png','where_is.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668041/missing/kboisw7jisdmi6f31xpk.png','missing/kboisw7jisdmi6f31xpk',2256442),(225,'missing','png','trace_path.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668045/missing/saeytjyh5udrnkakbkvw.png','missing/saeytjyh5udrnkakbkvw',1753896),(226,'missing','png','emotions.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668049/missing/mgyu4atuzv2p5dsjgv0r.png','missing/mgyu4atuzv2p5dsjgv0r',1640933),(227,'missing','png','cognition_package_cover.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668056/missing/ocbst107o90afuguhjsk.png','missing/ocbst107o90afuguhjsk',1293292),(228,'missing','png','color_sort.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668058/missing/r3hh6ff0t4x4kmmqj5nf.png','missing/r3hh6ff0t4x4kmmqj5nf',929524),(229,'missing','png','sound_match.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668062/missing/ypjubqocebtrco61sj2q.png','missing/ypjubqocebtrco61sj2q',1186056),(230,'missing','png','copy_animal.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668067/missing/fbssvqdqyct6yvwx9n5w.png','missing/fbssvqdqyct6yvwx9n5w',1840206),(231,'missing','png','match_alike.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668072/missing/na6yrw6gdfdzdaom8qbe.png','missing/na6yrw6gdfdzdaom8qbe',1155140),(232,'missing','png','put_object.png','https://res.cloudinary.com/dl24wsi47/image/upload/v1782668076/missing/rxhwjitk8zagsuxy2x9o.png','missing/rxhwjitk8zagsuxy2x9o',1275082);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `notifications`
--
-- No data required to be seeded here for now


--
-- Dumping data for table `package_images`
--
LOCK TABLES `package_images` WRITE;
/*!40000 ALTER TABLE `package_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_images` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `packages`
--
LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (1,'تعرّف على المفرد والجمع','مفرد و جمع',1),(2,'تعرّف على المذكر والمؤنث','مذكر ومؤنث',18),(3,'تمارين معرفية تساعد الطفل على بناء المهارات الأساسية اللازمة لتطوير الكلام والتواصل، مثل الانتباه، والتركيز، وتقليد الآخرين، واللعب الرمزي.','تمارين الادراك',161);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `patient`
--
LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `questions`
--
LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES
(1,'SINGULAR',1,'اختر الصيغة المفردة',1),
(2,'PLURAL',2,'اختر الصيغة الجمع',1),
(3,'SINGULAR',3,'أي من هذه مفرد؟',1),
(4,'PLURAL',4,'أي من هذه جمع؟',1),

(5,'MALE',1,'اختر الاسم المذكر',2),
(6,'FEMALE',2,'اختر الاسم المؤنث',2),
(7,'MALE',3,'أي من هذه مذكر؟',2),
(8,'FEMALE',4,'أي من هذه مؤنث؟',2),
(9,'MALE',5,'اختر الاسم المذكر',2),
(10,'FEMALE',6,'اختر الاسم المؤنث',2),
(11,'MALE',7,'أي من هذه مذكر؟',2),
(12,'FEMALE',8,'أي من هذه مؤنث؟',2),
(13,'MALE',9,'اختر الاسم المذكر',2),
(14,'FEMALE',10,'اختر الاسم المؤنث',2),
(15,'MALE',11,'أي من هذه مذكر؟',2),
(16,'FEMALE',12,'أي من هذه مؤنث؟',2),
(17,'MALE',13,'اختر الاسم المذكر',2),
(18,'FEMALE',14,'اختر الاسم المؤنث',2),
(19,'MALE',15,'أي من هذه مذكر؟',2),
(20,'FEMALE',16,'أي من هذه مؤنث؟',2);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `sounds`
--
LOCK TABLES `sounds` WRITE;
/*!40000 ALTER TABLE `sounds` DISABLE KEYS */;
INSERT INTO `sounds` VALUES (1,'trace_path','mp3','trace_path/fiuzxsex6f24gzobxtzz',70988,'cow_grass.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597818/trace_path/fiuzxsex6f24gzobxtzz.mp3'),(2,'trace_path','mp3','trace_path/jpbebquygrcygooorevn',68898,'monkey_banana.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597819/trace_path/jpbebquygrcygooorevn.mp3'),(3,'trace_path','mp3','trace_path/mugba3effdrrep2iwtng',71824,'rabbit_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597827/trace_path/mugba3effdrrep2iwtng.mp3'),(4,'trace_path','mp3','trace_path/wxudqccztlhgpukyxwcb',69316,'lion_meat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597829/trace_path/wxudqccztlhgpukyxwcb.mp3'),(5,'color_sort','mp3','color_sort/xv6rchn6dlvncqkvjozk',51762,'blue.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597839/color_sort/xv6rchn6dlvncqkvjozk.mp3'),(6,'color_sort','mp3','color_sort/bc4i1xhlqwjmfvzfhakp',53016,'green.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597841/color_sort/bc4i1xhlqwjmfvzfhakp.mp3'),(7,'color_sort','mp3','color_sort/ovc8xh3fnf2mgwuf14vm',49254,'red.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597843/color_sort/ovc8xh3fnf2mgwuf14vm.mp3'),(8,'color_sort','mp3','color_sort/wxklvwpdhwmit0bbndsy',66391,'match_balloon.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597844/color_sort/wxklvwpdhwmit0bbndsy.mp3'),(9,'color_sort','mp3','color_sort/vu5yrtpyntseezdphpjw',51762,'white.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597849/color_sort/vu5yrtpyntseezdphpjw.mp3'),(10,'color_sort','mp3','color_sort/miher47yoevj22aiw2kh',51762,'black.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597852/color_sort/miher47yoevj22aiw2kh.mp3'),(11,'color_sort','mp3','color_sort/vrjbzfurdpj8y4jd8snl',49254,'yellow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597853/color_sort/vrjbzfurdpj8y4jd8snl.mp3'),(12,'copy_animal','mp3','copy_animal/xvepryltbuyl9lci1nev',79347,'bear_wave.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597857/copy_animal/xvepryltbuyl9lci1nev.mp3'),(13,'copy_animal','mp3','copy_animal/tpv4rhkrllvhqq7gup1l',75586,'bear_jump.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597858/copy_animal/tpv4rhkrllvhqq7gup1l.mp3'),(14,'copy_animal','mp3','copy_animal/blshp8vbirlhy5xit4eu',57613,'q1_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597861/copy_animal/blshp8vbirlhy5xit4eu.mp3'),(15,'copy_animal','mp3','copy_animal/edku35wrb7ob6wrsopdg',67226,'bear_clap.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597865/copy_animal/edku35wrb7ob6wrsopdg.mp3'),(16,'copy_animal','mp3','copy_animal/gc48yb8111jfobqntrfb',76839,'q2_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597877/copy_animal/gc48yb8111jfobqntrfb.mp3'),(17,'emotions','mp3','emotions/ufwxmogg0gniyeoa0xih',73914,'rabbit_scene_scared.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597895/emotions/ufwxmogg0gniyeoa0xih.mp3'),(18,'emotions','mp3','emotions/zi8bna5ywbi3mu3cayr0',75586,'rabbit_scene_happy.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597896/emotions/zi8bna5ywbi3mu3cayr0.mp3'),(19,'emotions','mp3','emotions/c0i9xytrwhzuxbf9wrmg',50926,'scared.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597901/emotions/c0i9xytrwhzuxbf9wrmg.mp3'),(20,'emotions','mp3','emotions/vdzytdxguovuxtlvt5vt',75586,'rabbit_scene_sad.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597911/emotions/vdzytdxguovuxtlvt5vt.mp3'),(21,'emotions','mp3','emotions/bpu3j3rxjey3tkodbj1n',50090,'sad.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597913/emotions/bpu3j3rxjey3tkodbj1n.mp3'),(22,'emotions','mp3','emotions/piaiikqw95tiv5aifp1a',49254,'angry.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597915/emotions/piaiikqw95tiv5aifp1a.mp3'),(23,'emotions','mp3','emotions/i36npivtrivo0rf8z1xg',77675,'rabbit_scene_angry.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597919/emotions/i36npivtrivo0rf8z1xg.mp3'),(24,'emotions','mp3','emotions/so7piphwy86h2mmkhae6',71824,'rabbit_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597923/emotions/so7piphwy86h2mmkhae6.mp3'),(25,'feed_bear','mp3','feed_bear/fcswlhnipgkye39892lf',96237,'crunch_apple.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597935/feed_bear/fcswlhnipgkye39892lf.mp3'),(26,'feed_bear','mp3','feed_bear/w6tu6k4pildlq825lvmb',48960,'crunch.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597940/feed_bear/w6tu6k4pildlq825lvmb.mp3'),(27,'feed_bear','mp3','feed_bear/qfh8cku4xyrvxdia2rc5',209815,'crunch_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597942/feed_bear/qfh8cku4xyrvxdia2rc5.mp3'),(28,'feed_bear','mp3','feed_bear/qujvv8yzicgehuc552yp',64845,'crunch_banana.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597943/feed_bear/qujvv8yzicgehuc552yp.mp3'),(29,'feed_bear','mp3','feed_bear/xgpfhfsk6w0fptiqmnof',62629,'hungry_bear.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597945/feed_bear/xgpfhfsk6w0fptiqmnof.mp3'),(30,'match_alike','mp3','match_alike/sfl5qoaf2aeylxz2u5ox',80601,'choose_alike.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597959/match_alike/sfl5qoaf2aeylxz2u5ox.mp3'),(31,'put_object','mp3','put_object/bz2nkiwskh1a1kmnxxiv',66391,'put_the_ball_under_table.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597970/put_object/bz2nkiwskh1a1kmnxxiv.mp3'),(32,'sound_match','mp3','sound_match/rjvyraca0n75uo6vhcom',40124,'dog.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597977/sound_match/rjvyraca0n75uo6vhcom.mp3'),(33,'sound_match','mp3','sound_match/zgvbz7wepcwb2wk7vyeu',39168,'cat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597979/sound_match/zgvbz7wepcwb2wk7vyeu.mp3'),(34,'sound_match','mp3','sound_match/mrbmkzfp2urilnnrktcc',36000,'duck.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597980/sound_match/mrbmkzfp2urilnnrktcc.mp3'),(35,'sound_match','mp3','sound_match/ikjm5uppxzsbrexlyjxe',131239,'lion.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597982/sound_match/ikjm5uppxzsbrexlyjxe.mp3'),(36,'sound_match','mp3','sound_match/qjjrqybp2hsrfw4mho4y',20898,'sheep.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597984/sound_match/qjjrqybp2hsrfw4mho4y.mp3'),(37,'sound_match','mp3','sound_match/ggly1zh9okdx4x2rrvh9',42240,'cow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597985/sound_match/ggly1zh9okdx4x2rrvh9.mp3'),(38,'sound_match','mp3','sound_match/xoprfrvvtedt0xjzbkvo',109440,'listen_choose.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782597987/sound_match/xoprfrvvtedt0xjzbkvo.mp3'),(39,'where_is','mp3','where_is/ppcq3rvvenejkmjidtqk',55524,'where_is_the_dog.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598006/where_is/ppcq3rvvenejkmjidtqk.mp3'),(40,'where_is','mp3','where_is/hbdmvt7ldpxpeyxdgdjj',52598,'where_is_the_cat.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598008/where_is/hbdmvt7ldpxpeyxdgdjj.mp3'),(41,'where_is','mp3','where_is/ngebfziwzxbdy5ntsnfe',56777,'where_is_the_car.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598018/where_is/ngebfziwzxbdy5ntsnfe.mp3'),(42,'where_is','mp3','where_is/lfvq9os8zjkrvdeceixy',52598,'where_is_the_ball.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598020/where_is/lfvq9os8zjkrvdeceixy.mp3'),(43,'where_is','mp3','where_is/epyyetec8pkshsz12zxa',53016,'where_is_the_cow.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598024/where_is/epyyetec8pkshsz12zxa.mp3'),(44,'where_is','mp3','where_is/edsabzmib63zvawmob46',55524,'where_is_the_carrot.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598033/where_is/edsabzmib63zvawmob46.mp3'),(45,'where_is','mp3','where_is/ecxfvk5prve0juxjuaa4',54688,'where_is_the_apple.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782598035/where_is/ecxfvk5prve0juxjuaa4.mp3'),(46,'put_object','mp3','put_object/y2hfy2byd1eyf3j3by8p',68898,'put_the_ball_in_left.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668110/put_object/y2hfy2byd1eyf3j3by8p.mp3'),(47,'put_object','mp3','put_object/eif5mxvtsbidlv94yytl',68898,'put_the_ball_on_top.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668112/put_object/eif5mxvtsbidlv94yytl.mp3'),(48,'put_object','mp3','put_object/vnulqtlui9010y7tzx1h',50926,'happy.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668113/put_object/vnulqtlui9010y7tzx1h.mp3'),(49,'put_object','mp3','put_object/fxapl3uvnlww2ue3ws0z',66391,'put_the_ball_under_table.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668115/put_object/fxapl3uvnlww2ue3ws0z.mp3'),(50,'put_object','mp3','put_object/x44pkgi4ifhlfoxifjja',67226,'put_the_ball_in_right.mp3','https://res.cloudinary.com/dl24wsi47/video/upload/v1782668116/put_object/x44pkgi4ifhlfoxifjja.mp3');
/*!40000 ALTER TABLE `sounds` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `specialist`
--
LOCK TABLES `specialist` WRITE;
/*!40000 ALTER TABLE `specialist` DISABLE KEYS */;
INSERT INTO `specialist` VALUES ('cairo - haram', '2005-01-27', 'SP1', 1),('alexandira - smouha', '2003-11-21','SP2',2);
/*!40000 ALTER TABLE `specialist` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `specialist-personal_image`
--
LOCK TABLES `specialist-personal_image` WRITE;
/*!40000 ALTER TABLE `specialist-personal_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialist-personal_image` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `task_results`
--
-- No data required to be seeded here for now


--
-- Dumping data for table `tasks`
--
LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'التطابق والتصنيف','تمرين يقوم فيه الطفل بسحب البالونات الملونة إلى البقع اللونية المطابقة لها', 1,'color_sort','تمرين مطابقة الألوان', 162, 3),
                          (2,'التقليد والاستجابة','يظهر دب يقوم بحركات مختلفة (تصفيق، تلويح، قفز) ويطلب من اختيار ما يفعله الدب وتقليده.', 2,'copy_animal','تمرين التقليد ', 164, 3),
                          (3,'الذكاء العاطفي والتعرف على المشاعر','يُعرض مشهد لأرنب يعبر عن شعور معين (سعيد، حزين، غاضب، خائف) ويطلب من الطفل تحديد الشعور الصحيح', 3,'emotions','تمرين التعرف على المشاعر', 160, 3),
                          (4,'صيغة الطلب والتواصل','تمرين يطلب الدب من الطفل اطعامه ويقوم الطفل بسحب أنواع مختلفة من الطعام إلى فم الدب لإطعامه ', 4,'feed_bear','تمرين إطعام الدب', 157, 3),
                          (5,'التمييز البصري والانتباه',' يُطلب من الطفل العثور على صورة معينة من بين مجموعة من الصور المختلفة', 5,'where_is','تمرين أين ...؟', 158, 3),
                          (6,'الذاكرة والتطابق','تمرين ذاكرة بصري حيث يجب على الطفل العثور على الأزواج المتشابهة من البطاقات', 6,'match_alike','تمرين مطابقة المتشابهات', 165, 3),
                          (7,'المفاهيم المكانية والإدراك الحسي','يُطلب من الطفل وضع كرة في مكان معين بالنسبة للطاولة (فوق، تحت، يسار، يمين)', 7,'put_object','تمرين وضع الكرة ', 166, 3),
                          (8,'التمييز السمعي والربط بين الصوت والصورة',' يتم تشغيل صوت معين ويطلب من الطفل اختيار الصورة الصحيحة', 8,'sound_match','تمرين مطابقةالأصوات', 163, 3),
                          (9,'المهارات الحركية الدقيقة والتنسيق بين العين واليد',' يظهر مسار متقطع بين صورتين وعلى الطفل تتبع المسار بإصبعه من البداية إلى النهاية', 9,'trace_path','تمرين تتبع المسار', 159, 3);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
('SPECIALIST', 1, 25, CURRENT_TIMESTAMP, NULL,'user1@gmail.com', NULL,'FEMALE', 0, 'Dr. Noha Mohsen', '$2a$10$fXQxY3JcEplaLXM09pLcXextFFsASEvF1NmBkEzlSSzKNvN.efQFC', '01140954022', CURRENT_TIMESTAMP),
('SPECIALIST', 2, 30, CURRENT_TIMESTAMP, NULL,'user2@example.com', NULL,'MALE', 0, 'Dr. Ali Tamer', '$2a$10$BQqvEgM1RGCU0rbYs6sBjuoKcidrga1TIO/U2cbfz/1azQTYjkm.q', '01140954023', CURRENT_TIMESTAMP);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_answers`
--
LOCK TABLES `user_answers` WRITE;
/*!40000 ALTER TABLE `user_answers` DISABLE KEYS */;
INSERT INTO `user_answers` VALUES (1,_binary '',NULL,1,1,1),(2,_binary '',NULL,2,1,3),(3,_binary '',NULL,3,1,5),(4,_binary '',NULL,4,1,7),(5,_binary '\0',NULL,1,2,2),(6,_binary '',NULL,2,2,3),(7,_binary '',NULL,3,2,5),(8,_binary '',NULL,4,2,7),(9,_binary '',NULL,1,3,1),(10,_binary '',NULL,2,3,3),(11,_binary '',NULL,3,3,5),(12,_binary '',NULL,4,3,7),(13,_binary '',NULL,1,4,1),(14,_binary '',NULL,2,4,3),(15,_binary '',NULL,3,4,5),(16,_binary '',NULL,4,4,7);
/*!40000 ALTER TABLE `user_answers` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_sessions`
--
LOCK TABLES `user_sessions` WRITE;
/*!40000 ALTER TABLE `user_sessions` DISABLE KEYS */;
INSERT INTO `user_sessions` VALUES (1,'2026-05-09 01:09:39.336645',4,4,123,1),(2,'2026-05-09 01:09:56.821116',3,4,123,1),(3,'2026-05-09 01:13:08.828652',4,4,123,1),(4,'2026-06-16 21:34:36.840396',4,4,123,1);
/*!40000 ALTER TABLE `user_sessions` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `verification_code`
--
LOCK TABLES `verification_code` WRITE;
/*!40000 ALTER TABLE `verification_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_code` ENABLE KEYS */;
UNLOCK TABLES;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
