CREATE TABLE IF NOT EXISTS `images` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `image_url` VARCHAR(1000) NOT NULL,
    `image_name` VARCHAR(255) NOT NULL,
    `folder_name` VARCHAR(255) NOT NULL,
    `public_id` VARCHAR(255) DEFAULT NULL,
    `size` BIGINT DEFAULT NULL,
    `format` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_images_public_id` (`public_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `sounds` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `sound_url` VARCHAR(1000) NOT NULL,
    `sound_name` VARCHAR(255) NOT NULL,
    `folder_name` VARCHAR(255) NOT NULL,
    `public_id` VARCHAR(255) DEFAULT NULL,
    `size` BIGINT DEFAULT NULL,
    `format` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sounds_public_id` (`public_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `packages` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `cover_image_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_packages_cover_image` (`cover_image_id`),
    CONSTRAINT `fk_packages_cover_image` FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `package_images` (
    `package_id` BIGINT NOT NULL,
    `image_id` BIGINT NOT NULL,
    PRIMARY KEY (`package_id`, `image_id`),
    KEY `idx_package_images_image` (`image_id`),
    CONSTRAINT `fk_package_images_package` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
    CONSTRAINT `fk_package_images_image` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `questions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `package_id` BIGINT NOT NULL,
    `question_text` VARCHAR(255) DEFAULT NULL,
    `correct_answer` VARCHAR(255) DEFAULT NULL,
    `order_index` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_questions_package` (`package_id`),
    CONSTRAINT `fk_questions_package` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `choices` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `question_id` BIGINT NOT NULL,
    `image_id` BIGINT DEFAULT NULL,
    `type` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_choices_question` (`question_id`),
    KEY `idx_choices_image` (`image_id`),
    CONSTRAINT `fk_choices_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
    CONSTRAINT `fk_choices_image` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user_sessions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT DEFAULT NULL,
    `package_id` BIGINT NOT NULL,
    `score` INT NOT NULL,
    `total_questions` INT NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_user_sessions_package` (`package_id`),
    CONSTRAINT `fk_user_sessions_package` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user_answers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `session_id` BIGINT NOT NULL,
    `question_id` BIGINT NOT NULL,
    `choice_id` BIGINT DEFAULT NULL,
    `selected_type` VARCHAR(255) DEFAULT NULL,
    `is_correct` BIT(1) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_user_answers_session` (`session_id`),
    KEY `idx_user_answers_question` (`question_id`),
    KEY `idx_user_answers_choice` (`choice_id`),
    CONSTRAINT `fk_user_answers_session` FOREIGN KEY (`session_id`) REFERENCES `user_sessions` (`id`),
    CONSTRAINT `fk_user_answers_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
    CONSTRAINT `fk_user_answers_choice` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `categories` (
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `cases` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT DEFAULT NULL,
    `child_name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `tasks` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `package_id` BIGINT NOT NULL,
    `task_key` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `image_id` BIGINT DEFAULT NULL,
    `category` VARCHAR(255) DEFAULT NULL,
    `order_index` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tasks_task_key` (`task_key`),
    KEY `idx_tasks_package` (`package_id`),
    KEY `idx_tasks_image` (`image_id`),
    CONSTRAINT `fk_tasks_package` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
    CONSTRAINT `fk_tasks_image` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `exercise_items` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `task_id` BIGINT NOT NULL,
    `item_key` VARCHAR(255) DEFAULT NULL,
    `label` VARCHAR(255) DEFAULT NULL,
    `image_id` BIGINT DEFAULT NULL,
    `sound_id` BIGINT DEFAULT NULL,
    `order_index` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_exercise_items_task` (`task_id`),
    KEY `idx_exercise_items_image` (`image_id`),
    KEY `idx_exercise_items_sound` (`sound_id`),
    CONSTRAINT `fk_exercise_items_task` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
    CONSTRAINT `fk_exercise_items_image` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`),
    CONSTRAINT `fk_exercise_items_sound` FOREIGN KEY (`sound_id`) REFERENCES `sounds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `task_results` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `case_id` BIGINT NOT NULL,
    `task_id` INT NOT NULL,
    `task_name` VARCHAR(255) NOT NULL,
    `started_at` DATETIME(6) NOT NULL,
    `completed_at` DATETIME(6) NOT NULL,
    `completed` BIT(1) NOT NULL,
    `duration_seconds` INT NOT NULL,
    `total_rounds` INT NOT NULL,
    `correct_rounds` INT NOT NULL,
    `accuracy` DECIMAL(5,4) DEFAULT NULL,
    `attempts_count` INT NOT NULL,
    `avg_reaction_ms` INT DEFAULT NULL,
    `error_breakdown` JSON NOT NULL,
    `extra` JSON NOT NULL,
    `created_at` DATETIME(6) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_task_results_idempotency` (`case_id`, `task_id`, `started_at`),
    KEY `idx_task_results_case` (`case_id`),
    KEY `idx_task_results_task` (`task_id`),
    KEY `idx_task_results_started` (`case_id`, `started_at`),
    CONSTRAINT `fk_task_results_case` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `images` VALUES
    (1,'singular',NULL,'تفاحة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275729/singular/je4ee3ynephfdyiiyamt.jpg',NULL,NULL),
    (2,'singular',NULL,'موزة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275731/singular/i6j8bgo8hjomrndnuf9f.jpg',NULL,NULL),
    (3,'singular',NULL,'كتاب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275732/singular/jiobbipsj6u6bgl1scir.jpg',NULL,NULL),
    (4,'singular',NULL,'قلم.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275733/singular/vixug70p9p3t6ixe1syw.jpg',NULL,NULL),
    (5,'plural',NULL,'تفاح.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275734/plural/eg0islvmmh2q10fnevmj.jpg',NULL,NULL),
    (6,'plural',NULL,'موز.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275736/plural/vbygzwguwc4ihtrp8uth.jpg',NULL,NULL),
    (7,'plural',NULL,'اقلام.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275737/plural/nzarm4mqfote1ehibiry.jpg',NULL,NULL),
    (8,'plural',NULL,'كتب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275738/plural/pkvtilp52igwc2ylyyrh.jpg',NULL,NULL);

INSERT IGNORE INTO `packages` VALUES (1,'تعرّف على المفرد والجمع','مفرد و جمع',1);

INSERT IGNORE INTO `questions` VALUES
    (1,1,'اختر الصيغة المفردة','SINGULAR',1),
    (2,1,'اختر الصيغة الجمع','PLURAL',1),
    (3,1,'أي من هذه مفرد؟','SINGULAR',2),
    (4,1,'أي من هذه جمع؟','PLURAL',2);

INSERT IGNORE INTO `choices` VALUES
    (1,1,1,'SINGULAR'),
    (2,1,5,'PLURAL'),
    (3,2,5,'PLURAL'),
    (4,2,1,'SINGULAR'),
    (5,3,3,'SINGULAR'),
    (6,3,7,'PLURAL'),
    (7,4,8,'PLURAL'),
    (8,4,4,'SINGULAR');

INSERT IGNORE INTO `user_sessions` VALUES
    (1,123,1,4,4,'2026-05-09 01:09:39.336645'),
    (2,123,1,3,4,'2026-05-09 01:09:56.821116'),
    (3,123,1,4,4,'2026-05-09 01:13:08.828652'),
    (4,123,1,4,4,'2026-06-16 21:34:36.840396');

INSERT IGNORE INTO `user_answers`
(id, is_correct, selected_type, question_id, session_id, choice_id)
VALUES
    (1, 1, NULL, 1, 1, 1),
    (2, 1, NULL, 2, 1, 3),
    (3, 1, NULL, 3, 1, 5),
    (4, 1, NULL, 4, 1, 7),

    (5, 0, NULL, 1, 2, 2),
    (6, 1, NULL, 2, 2, 3),
    (7, 1, NULL, 3, 2, 5),
    (8, 1, NULL, 4, 2, 7),

    (9, 1, NULL, 1, 3, 1),
    (10, 1, NULL, 2, 3, 3),
    (11, 1, NULL, 3, 3, 5),
    (12, 1, NULL, 4, 3, 7),

    (13, 1, NULL, 1, 4, 1),
    (14, 1, NULL, 2, 4, 3),
    (15, 1, NULL, 3, 4, 5),
    (16, 1, NULL, 4, 4, 7);