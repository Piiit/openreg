
/*
Peter Moser peter.moser@stud-inf.unibz.it
Valentin Huber valentin.huber@stud-inf.unibz.it
Johannes Ganthaler johannes.ganthaler@stud-inf.unibz.it

ds_group2
*/

INSERT INTO ability_description (description) VALUES ('Dyskalkulie');
INSERT INTO ability_description (description) VALUES ('Legasthenie');
INSERT INTO ability_description (description) VALUES ('Familiäre Probleme');
INSERT INTO ability_description (description) VALUES ('ADS');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Boznerstr.', '14', '39100', 'Bozen', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Meranerstr.', '10', '39011', 'Lana', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Winkelweg', '4', '39012', 'Meran', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Alessandriastr.', '47', '39100', 'Bozen', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Musterstr.', '10', '39010', 'Frangart', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Ifingerstr.', '13', '39012', 'Meran', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Schafferstr.', '6', '39012', 'Meran', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Haugenstr.', '18', '39011', 'Lana', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Leichterstr.', '11', '39011', 'Lana', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Zollstr.', '23', '39011', 'Lana', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Rossistr.', '1', '39010', 'Riffian', 'Italy');
INSERT INTO address (street, no, zip_code, city, country) VALUES ('Kirchweg', '7', '39010', 'Riffian', 'Italy');
INSERT INTO assessment_type (description) VALUES ('Frage');
INSERT INTO assessment_type (description) VALUES ('Schularbeit');
INSERT INTO assessment_type (description) VALUES ('Mündliche Prüfung');
INSERT INTO assessment_type (description) VALUES ('Test');
INSERT INTO assessment_type (description) VALUES ('Gruppenarbeit');
INSERT INTO assessment_type (description) VALUES ('Vortrag');
INSERT INTO assessment_type (description) VALUES ('Zeugnisnote (1. Semester)');
INSERT INTO assessment_type (description) VALUES ('Zeugnisnote (2. Semester)');
INSERT INTO assessment_type (description) VALUES ('Fähigkeit');
INSERT INTO assessment_type (description) VALUES ('Fertigkeit');
INSERT INTO course (name, credit_points) VALUES ('Mathematik', NULL);
INSERT INTO course (name, credit_points) VALUES ('Naturkunde', NULL);
INSERT INTO course (name, credit_points) VALUES ('Deutsch', NULL);
INSERT INTO course (name, credit_points) VALUES ('Englisch', NULL);
INSERT INTO course (name, credit_points) VALUES ('Italienisch', NULL);
INSERT INTO course (name, credit_points) VALUES ('Geschichte', NULL);
INSERT INTO course (name, credit_points) VALUES ('Erdkunde', NULL);
INSERT INTO course (name, credit_points) VALUES ('(all)', NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Algebra', 1, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Sexualkunde', 2, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Literaturkunde', 3, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Passato prossimo', 5, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('2. Weltkrieg', 6, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Geologie', 7, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Grammar', 4, NULL);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Holocaust', 6, 6);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Pearl Harbor', 6, 6);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Lineare Gleichungen', 1, 1);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Quadratische Gleichungen', 1, 1);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Gleichungssysteme', 1, 1);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Sedimentgesteine', 7, 7);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Irregular verbs', 4, 4);
INSERT INTO topic (description, course_id, topic_id) VALUES ('Natürliche Zahlen', 1, NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (1, 15, 'Koordinatensystem', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (2, 15, '1. Schularbeit', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (1, 15, 'Diagramme zeichnen', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (1, 15, 'Vorg��nger und Nachfolger', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (1, 15, 'R��mische Zahlen', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (1, 15, 'Zehnerpotenzen', NULL);
INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (3, 15, '1x1', NULL);
INSERT INTO class (level, stream, notes) VALUES ('1', 'A', NULL);
INSERT INTO class (level, stream, notes) VALUES ('1', 'B', NULL);
INSERT INTO class (level, stream, notes) VALUES ('2', 'A', NULL);
INSERT INTO class (level, stream, notes) VALUES ('2', 'B', NULL);
INSERT INTO mark_type (description) VALUES ('German System');
INSERT INTO mark_type (description) VALUES ('Italian System');
INSERT INTO mark_type (description) VALUES ('Roman System');
INSERT INTO mark_type (description) VALUES ('Custom1');
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('10', 100, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('9', 90, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('8', 80, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('7', 70, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('6', 60, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('5', 50, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('+', 100, 4);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('~', 75, 4);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('-', 50, 4);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('9/10', 95, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('8/9', 85, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('7/8', 75, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('6/7', 65, 2);
INSERT INTO mark (representation, bound, mark_type_id) VALUES ('5/6', 55, 2);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Andi', 'Latte', '1995-06-07', 1, 1, '3334567897', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Luis ', 'Amplatz', '1996-03-15', 3, 2, '123345678', 2012, 1, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Wendy', 'Lator', '1995-09-09', 5, 1, '3338899879', 2012, 1, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Wanda', 'Stock', '1995-11-21', 6, 1, '12434734737', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Jean', 'Darmerie', '1995-12-12', 7, 2, '21131435', 2012, 3, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Phil', 'Stifte', '1995-11-30', 8, 2, '3536526', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Gitta', 'Stäbe', '1995-11-11', 9, 2, '5615134626', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Ruth', 'Schen', '1995-01-09', 10, 1, '34557453686', 2012, 4, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Arno', 'Nym', '1995-03-01', 11, 2, '4532645', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Lilli', 'Putana', '1995-04-12', 12, 1, '423543523', 2012, NULL, NULL, NULL);
INSERT INTO student (name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES ('Franz', 'Hofbauer', '1995-04-22', 4, 1, '12345678', 2012, 1, NULL, NULL);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 2, 2);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 3, 4);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 4, 2);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 5, 2);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 6, 3);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (1, 1, 10);
INSERT INTO assessment_student (student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (1, 1, 2, NULL, 50, '2012-12-12', true, NULL);
INSERT INTO assessment_student (student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (1, 1, 3, NULL, 100, '2012-12-12', true, NULL);
INSERT INTO assessment_student (student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (1, 1, 4, NULL, 100, '2012-12-12', true, NULL);
INSERT INTO assessment_student (student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (1, 1, 1, 5, NULL, '2012-12-12', true, NULL);
INSERT INTO teacher (name, surname, login, password, birthday, address_id, phone_number, picture, notes) VALUES ('Hons', 'Gun Taylor', 'hons', 'yui888', '1991-04-22', 2, '7654332', NULL, NULL);
INSERT INTO teacher (name, surname, login, password, birthday, address_id, phone_number, picture, notes) VALUES ('Patrizio', 'Albero', 'palbero', '12.45-+~.1', '1992-05-17', 1, '1234567', NULL, NULL);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 1, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 2, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 3, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 4, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 1, 2);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 4, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 3, 4);