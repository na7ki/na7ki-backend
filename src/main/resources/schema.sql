SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `choices`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `images`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `package_images`;
CREATE TABLE `package_images` (
  `package_id` bigint NOT NULL,
  `image_id` bigint NOT NULL,
  KEY `FKcbk0upk1lv8acrww36r0hiesu` (`image_id`),
  KEY `FKckjgwbelio5r7ktswj44vbuxb` (`package_id`),
  CONSTRAINT `FKcbk0upk1lv8acrww36r0hiesu` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`),
  CONSTRAINT `FKckjgwbelio5r7ktswj44vbuxb` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `cover_image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKisc0pvihkewgp2tp0omxkk9vm` (`cover_image_id`),
  CONSTRAINT `FKisc0pvihkewgp2tp0omxkk9vm` FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `patient`;
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

DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  `question_text` varchar(255) DEFAULT NULL,
  `package_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe7og4n49t9oc5dkqd9bgtfof6` (`package_id`),
  CONSTRAINT `FKe7og4n49t9oc5dkqd9bgtfof6` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `specialist`;
CREATE TABLE `specialist` (
  `address` varchar(300) NOT NULL,
  `date_of_birth` date NOT NULL,
  `specialist_id` varchar(15) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK34mr056f0dytcik6sdsj6nye` (`specialist_id`),
  CONSTRAINT `FKh3w6mud9ch7yau9tqr100r0vr` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `specialist-personal_images`;
CREATE TABLE `specialist-personal_images` (
  `user_id` bigint NOT NULL,
  `personal_image_path` varchar(100) NOT NULL,
  KEY `FK5j0uketxs6vajt9g6y28wx3a5` (`user_id`),
  CONSTRAINT `FK5j0uketxs6vajt9g6y28wx3a5` FOREIGN KEY (`user_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_answers`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_sessions`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `users`;
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
  CONSTRAINT `users_chk_1` CHECK ((`role` in ('PATIENT','SPECIALIST')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `verification_codes`;
CREATE TABLE `verification_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expires_at` datetime(6) NOT NULL,
  `four_digit_code` varchar(4) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbf0ofef2q09iwv2jg00aygy4q` (`user_id`),
  CONSTRAINT `FKa4qo6nts1xd94owirq5evcpda` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;