INSERT IGNORE  INTO `images` VALUES (1,'singular',NULL,'تفاحة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275729/singular/je4ee3ynephfdyiiyamt.jpg',NULL,NULL),(2,'singular',NULL,'موزة.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275731/singular/i6j8bgo8hjomrndnuf9f.jpg',NULL,NULL),(3,'singular',NULL,'كتاب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275732/singular/jiobbipsj6u6bgl1scir.jpg',NULL,NULL),(4,'singular',NULL,'قلم.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275733/singular/vixug70p9p3t6ixe1syw.jpg',NULL,NULL),(5,'plural',NULL,'تفاح.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275734/plural/eg0islvmmh2q10fnevmj.jpg',NULL,NULL),(6,'plural',NULL,'موز.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275736/plural/vbygzwguwc4ihtrp8uth.jpg',NULL,NULL),(7,'plural',NULL,'اقلام.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275737/plural/nzarm4mqfote1ehibiry.jpg',NULL,NULL),(8,'plural',NULL,'كتب.jpeg','https://res.cloudinary.com/dl24wsi47/image/upload/v1778275738/plural/pkvtilp52igwc2ylyyrh.jpg',NULL,NULL);

INSERT IGNORE  INTO `packages` VALUES (1,'تعرّف على المفرد والجمع','مفرد و جمع',1);

INSERT IGNORE INTO `questions` VALUES (1,'SINGULAR',1,'اختر الصيغة المفردة',1),(2,'PLURAL',2,'اختر الصيغة الجمع',1),(3,'SINGULAR',3,'أي من هذه مفرد؟',1),(4,'PLURAL',4,'أي من هذه جمع؟',1);

INSERT IGNORE INTO `choices` VALUES (1,'SINGULAR',1,1),(2,'PLURAL',5,1),(3,'PLURAL',5,2),(4,'SINGULAR',1,2),(5,'SINGULAR',3,3),(6,'PLURAL',7,3),(7,'PLURAL',8,4),(8,'SINGULAR',4,4);

INSERT IGNORE INTO `user_sessions` VALUES (1,'2026-05-09 01:09:39.336645',4,4,123,1),(2,'2026-05-09 01:09:56.821116',3,4,123,1),(3,'2026-05-09 01:13:08.828652',4,4,123,1),(4,'2026-06-16 21:34:36.840396',4,4,123,1);

INSERT IGNORE INTO user_answers
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