-- Insert packages
INSERT INTO packages (id, title, description, cover_image_id) VALUES 
(1, 'مفرد و جمع', 'تعرّف على المفرد والجمع', 1);

-- Insert questions (linking to package 1)
INSERT INTO questions (id, package_id, question_text, correct_answer, order_index) VALUES
(1, 1, 'اختر الصيغة المفردة', 'SINGULAR', 1),
(2, 1, 'اختر الصيغة الجمع', 'PLURAL', 2),
(3, 1, 'أي من هذه مفرد؟', 'SINGULAR', 3),
(4, 1, 'أي من هذه جمع؟', 'PLURAL', 4);

-- Insert choices for Question 1 (مفرد و جمع)
-- Q1: اختر الصيغة المفردة
INSERT INTO choices (id, question_id, image_id, type) VALUES
(1, 1, 1, 'SINGULAR'),   -- تفاحة (singular)
(2, 1, 5, 'PLURAL');     -- تفاح (plural)

-- Q2: اختر الصيغة الجمع
INSERT INTO choices (id, question_id, image_id, type) VALUES
(3, 2, 5, 'PLURAL'),     -- تفاح (plural)
(4, 2, 1, 'SINGULAR');   -- تفاحة (singular)

-- Q3: أي من هذه مفرد؟
INSERT INTO choices (id, question_id, image_id, type) VALUES
(5, 3, 3, 'SINGULAR'),   -- كتاب (singular)
(6, 3, 7, 'PLURAL');     -- اقلام (plural)

-- Q4: أي من هذه جمع؟
INSERT INTO choices (id, question_id, image_id, type) VALUES
(7, 4, 8, 'PLURAL'),     -- كتب (plural)
(8, 4, 4, 'SINGULAR');   -- قلم (singular)
