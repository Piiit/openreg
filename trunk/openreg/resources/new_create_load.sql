SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE TABLE ability_description (
    id bigint NOT NULL,
    description character varying NOT NULL
);
CREATE SEQUENCE ability_description_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE ability_description_id_seq OWNED BY ability_description.id;
CREATE TABLE address (
    id bigint NOT NULL,
    street character varying NOT NULL,
    no character varying NOT NULL,
    zip_code character varying NOT NULL,
    city character varying NOT NULL,
    country character varying NOT NULL
);
CREATE SEQUENCE address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE address_id_seq OWNED BY address.id;
CREATE TABLE assessment (
    id bigint NOT NULL,
    assessment_type_id bigint NOT NULL,
    topic_id bigint,
    description character varying,
    notes character varying
);
CREATE SEQUENCE assessment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE assessment_id_seq OWNED BY assessment.id;
CREATE TABLE assessment_student (
    id bigint NOT NULL,
    student_id bigint NOT NULL,
    weighted_assessment_main_id bigint NOT NULL,
    weighted_assessment_sub_id bigint NOT NULL,
    mark_id bigint,
    mark double precision,
    date date NOT NULL,
    differentiated_evaluation boolean,
    notes character varying,
    CONSTRAINT assessment_student_mark_check CHECK (((mark >= (0)::double precision) AND (mark <= (100)::double precision))),
    CONSTRAINT mark_id_or_mark_not_both CHECK ((((mark_id IS NULL) AND (mark IS NOT NULL)) OR ((mark_id IS NOT NULL) AND (mark IS NULL))))
);
CREATE SEQUENCE assessment_student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE assessment_student_id_seq OWNED BY assessment_student.id;
CREATE TABLE assessment_type (
    id bigint NOT NULL,
    description character varying NOT NULL
);
CREATE SEQUENCE assessment_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE assessment_type_id_seq OWNED BY assessment_type.id;
CREATE TABLE class (
    id bigint NOT NULL,
    level character varying NOT NULL,
    stream character varying,
    notes character varying
);
CREATE SEQUENCE class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE class_id_seq OWNED BY class.id;
CREATE TABLE course (
    id bigint NOT NULL,
    name character varying NOT NULL,
    credit_points integer
);
CREATE SEQUENCE course_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE course_id_seq OWNED BY course.id;
CREATE TABLE mark (
    id bigint NOT NULL,
    representation character varying NOT NULL,
    bound double precision NOT NULL,
    mark_type_id bigint NOT NULL,
    CONSTRAINT mark_bound_check CHECK (((bound >= (0)::double precision) AND (bound <= (100)::double precision)))
);
CREATE SEQUENCE mark_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE mark_id_seq OWNED BY mark.id;
CREATE TABLE mark_type (
    id bigint NOT NULL,
    description character varying NOT NULL
);
CREATE SEQUENCE mark_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE mark_type_id_seq OWNED BY mark_type.id;
CREATE TABLE student (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    birthday date NOT NULL,
    address_id bigint NOT NULL,
    class_id bigint NOT NULL,
    phonenumber character varying,
    enrolment_year integer NOT NULL,
    ability_description_id bigint,
    picture bit varying,
    notes character varying
);
CREATE SEQUENCE student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE student_id_seq OWNED BY student.id;
CREATE TABLE teacher (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    birthday date NOT NULL,
    address_id bigint NOT NULL,
    phone_number character varying,
    picture bit varying,
    notes text
);
CREATE TABLE teacher_class_course (
    teacher_id bigint NOT NULL,
    class_id bigint NOT NULL,
    course_id bigint NOT NULL
);
CREATE SEQUENCE teacher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE teacher_id_seq OWNED BY teacher.id;
CREATE TABLE topic (
    id bigint NOT NULL,
    description character varying NOT NULL,
    course_id bigint NOT NULL,
    topic_id bigint
);
CREATE SEQUENCE topic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE topic_id_seq OWNED BY topic.id;
CREATE TABLE weighted_assessment (
    main_assessment_id bigint NOT NULL,
    sub_assessment_id bigint NOT NULL,
    weight double precision NOT NULL,
    CONSTRAINT weighted_assessment_weight_check CHECK (((weight > (0)::double precision) AND (weight <= (100)::double precision)))
);
ALTER TABLE ONLY ability_description ALTER COLUMN id SET DEFAULT nextval('ability_description_id_seq'::regclass);
ALTER TABLE ONLY address ALTER COLUMN id SET DEFAULT nextval('address_id_seq'::regclass);
ALTER TABLE ONLY assessment ALTER COLUMN id SET DEFAULT nextval('assessment_id_seq'::regclass);
ALTER TABLE ONLY assessment_student ALTER COLUMN id SET DEFAULT nextval('assessment_student_id_seq'::regclass);
ALTER TABLE ONLY assessment_type ALTER COLUMN id SET DEFAULT nextval('assessment_type_id_seq'::regclass);
ALTER TABLE ONLY class ALTER COLUMN id SET DEFAULT nextval('class_id_seq'::regclass);
ALTER TABLE ONLY course ALTER COLUMN id SET DEFAULT nextval('course_id_seq'::regclass);
ALTER TABLE ONLY mark ALTER COLUMN id SET DEFAULT nextval('mark_id_seq'::regclass);
ALTER TABLE ONLY mark_type ALTER COLUMN id SET DEFAULT nextval('mark_type_id_seq'::regclass);
ALTER TABLE ONLY student ALTER COLUMN id SET DEFAULT nextval('student_id_seq'::regclass);
ALTER TABLE ONLY teacher ALTER COLUMN id SET DEFAULT nextval('teacher_id_seq'::regclass);
ALTER TABLE ONLY topic ALTER COLUMN id SET DEFAULT nextval('topic_id_seq'::regclass);
INSERT INTO ability_description (id, description) VALUES (1, 'Dyskalkulie');
INSERT INTO ability_description (id, description) VALUES (2, 'Legasthenie');
INSERT INTO ability_description (id, description) VALUES (4, 'ADS');
INSERT INTO ability_description (id, description) VALUES (3, 'Familiaere Probleme');
SELECT pg_catalog.setval('ability_description_id_seq', 6, true);
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (1, 'Boznerstr.', '14', '39100', 'Bozen', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (3, 'Winkelweg', '4', '39012', 'Meran', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (6, 'Ifingerstr.', '13', '39012', 'Meran', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (7, 'Schafferstr.', '6', '39012', 'Meran', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (8, 'Haugenstr.', '18', '39011', 'Lana', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (9, 'Leichterstr.', '11', '39011', 'Lana', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (11, 'Rossistr.', '1', '39010', 'Riffian', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (12, 'Kirchweg', '7', '39010', 'Riffian', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (2, 'Meranerstr.', '10', '39011', 'Lana', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (4, 'Zollstr.', '23', '39011', 'Lana', 'Italy');
INSERT INTO address (id, street, no, zip_code, city, country) VALUES (5, 'Musterstr.', '10', '39010', 'Frangart', 'Italy');
SELECT pg_catalog.setval('address_id_seq', 19, true);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (2, 2, 15, '1. Schularbeit', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (6, 1, 15, 'Zehnerpotenzen', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (7, 3, 15, '1x1', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (11, 12, NULL, '(all)', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (3, 1, 15, 'Zeichne Diagramme', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (1, 1, 15, 'Erstelle ein Koordinatensysteme', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (14, 6, 7, 'Talk about The Queen', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (9, 1, 5, 'Wann begann der 2. Weltkrieg?', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (5, 1, 15, 'Römische Zahlen', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (8, 2, 5, 'Test zum 2. Weltkrieg', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (4, 1, 15, 'Vorgänger und Nachfolger', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (10, 7, NULL, '1. Semester - Mathematik', NULL);
INSERT INTO assessment (id, assessment_type_id, topic_id, description, notes) VALUES (13, 7, NULL, '2. Semester - Mathematik', NULL);
SELECT pg_catalog.setval('assessment_id_seq', 24, true);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (1, 1, 2, 1, NULL, 3, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (2, 1, 2, 3, NULL, 5, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (3, 1, 2, 4, NULL, 2, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (4, 1, 2, 5, NULL, 4, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (5, 1, 2, 6, NULL, 6, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (6, 2, 2, 1, NULL, 6, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (7, 2, 2, 3, NULL, 6, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (8, 2, 2, 4, NULL, 2, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (9, 2, 2, 5, NULL, 4.5, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (10, 2, 2, 6, NULL, 5, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (11, 1, 10, 2, 3, NULL, '2000-05-05', NULL, NULL);
INSERT INTO assessment_student (id, student_id, weighted_assessment_main_id, weighted_assessment_sub_id, mark_id, mark, date, differentiated_evaluation, notes) VALUES (12, 2, 10, 2, 10, NULL, '2000-05-05', NULL, NULL);
SELECT pg_catalog.setval('assessment_student_id_seq', 4, true);
INSERT INTO assessment_type (id, description) VALUES (1, 'Frage');
INSERT INTO assessment_type (id, description) VALUES (2, 'Schularbeit');
INSERT INTO assessment_type (id, description) VALUES (4, 'Test');
INSERT INTO assessment_type (id, description) VALUES (5, 'Gruppenarbeit');
INSERT INTO assessment_type (id, description) VALUES (6, 'Vortrag');
INSERT INTO assessment_type (id, description) VALUES (10, 'Fertigkeit');
INSERT INTO assessment_type (id, description) VALUES (7, 'Zeugnisnote');
INSERT INTO assessment_type (id, description) VALUES (3, 'Mündliche Prüfung');
INSERT INTO assessment_type (id, description) VALUES (9, 'Fähigkeit');
INSERT INTO assessment_type (id, description) VALUES (13, 'Hausaufgabe');
INSERT INTO assessment_type (id, description) VALUES (12, '_ROOT_');
SELECT pg_catalog.setval('assessment_type_id_seq', 13, true);
INSERT INTO class (id, level, stream, notes) VALUES (1, '1', 'A', NULL);
INSERT INTO class (id, level, stream, notes) VALUES (2, '1', 'B', NULL);
INSERT INTO class (id, level, stream, notes) VALUES (3, '2', 'A', NULL);
INSERT INTO class (id, level, stream, notes) VALUES (4, '2', 'B', NULL);
SELECT pg_catalog.setval('class_id_seq', 14, true);
INSERT INTO course (id, name, credit_points) VALUES (1, 'Mathematik', 12);
INSERT INTO course (id, name, credit_points) VALUES (2, 'Naturkunde', 6);
INSERT INTO course (id, name, credit_points) VALUES (5, 'Italienisch', 8);
INSERT INTO course (id, name, credit_points) VALUES (6, 'Geschichte', 5);
INSERT INTO course (id, name, credit_points) VALUES (7, 'Erdkunde', 5);
INSERT INTO course (id, name, credit_points) VALUES (4, 'Englisch', 8);
INSERT INTO course (id, name, credit_points) VALUES (3, 'Deutsch', 8);
SELECT pg_catalog.setval('course_id_seq', 10, true);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (1, '10', 100, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (2, '9', 90, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (3, '8', 80, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (4, '7', 70, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (5, '6', 60, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (6, '5', 50, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (7, '+', 100, 4);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (8, '~', 75, 4);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (10, '9/10', 95, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (11, '8/9', 85, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (12, '7/8', 75, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (13, '6/7', 65, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (14, '5/6', 55, 2);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (15, '-', 50, 4);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (16, 'good', 100, 8);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (17, 'bad', 50, 8);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (18, 'ugly', 25, 8);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (19, '1', 100, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (20, '2', 90, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (21, '3', 80, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (22, '4', 70, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (23, '5', 60, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (24, '6', 50, 1);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (25, 'X', 100, 3);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (26, 'V', 50, 3);
INSERT INTO mark (id, representation, bound, mark_type_id) VALUES (27, 'VII', 70, 3);
SELECT pg_catalog.setval('mark_id_seq', 27, true);
INSERT INTO mark_type (id, description) VALUES (1, 'German System');
INSERT INTO mark_type (id, description) VALUES (2, 'Italian System');
INSERT INTO mark_type (id, description) VALUES (3, 'Roman System');
INSERT INTO mark_type (id, description) VALUES (4, '+~- Style');
INSERT INTO mark_type (id, description) VALUES (8, 'New Style');
SELECT pg_catalog.setval('mark_type_id_seq', 8, true);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (1, 'Andi', 'Latte', '1995-06-07', 1, 1, '3334567897', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (2, 'Luis ', 'Amplatz', '1996-03-15', 3, 2, '123345678', 2012, 1, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (3, 'Wendy', 'Lator', '1995-09-09', 5, 1, '3338899879', 2012, 1, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (4, 'Wanda', 'Stock', '1995-11-21', 6, 1, '12434734737', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (5, 'Jean', 'Darmerie', '1995-12-12', 7, 2, '21131435', 2012, 3, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (6, 'Phil', 'Stifte', '1995-11-30', 8, 2, '3536526', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (9, 'Arno', 'Nym', '1995-03-01', 11, 2, '4532645', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (10, 'Lilli', 'Putana', '1995-04-12', 12, 1, '423543523', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (11, 'Franz', 'Hofbauer', '1995-04-22', 2, 1, '12345678', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (8, 'Ruth', 'Schen', '1995-01-09', 4, 1, '34557453686', 2012, NULL, NULL, NULL);
INSERT INTO student (id, name, surname, birthday, address_id, class_id, phonenumber, enrolment_year, ability_description_id, picture, notes) VALUES (7, 'Gitta', 'Stäbe', '1995-11-11', 5, 2, '5615134626', 2012, NULL, NULL, NULL);
SELECT pg_catalog.setval('student_id_seq', 16, true);
INSERT INTO teacher (id, name, surname, login, password, birthday, address_id, phone_number, picture, notes) VALUES (1, 'Hons', 'Gun Taylor', 'hons', 'yui888', '1991-04-22', 2, '7654332', NULL, NULL);
INSERT INTO teacher (id, name, surname, login, password, birthday, address_id, phone_number, picture, notes) VALUES (2, 'Patrizio', 'Albero', 'palbero', '12.45-+~.1', '1992-05-17', 1, '1234567', NULL, NULL);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 1, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 2, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 3, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (1, 4, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 1, 2);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 4, 1);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 3, 4);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 3, 3);
INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (2, 1, 3);
SELECT pg_catalog.setval('teacher_id_seq', 2, true);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (1, 'Algebra', 1, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (2, 'Sexualkunde', 2, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (3, 'Literaturkunde', 3, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (4, 'Passato prossimo', 5, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (5, '2. Weltkrieg', 6, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (6, 'Geologie', 7, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (7, 'Grammar', 4, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (10, 'Lineare Gleichungen', 1, 1);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (11, 'Quadratische Gleichungen', 1, 1);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (12, 'Gleichungssysteme', 1, 1);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (13, 'Sedimentgesteine', 7, 7);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (14, 'Irregular verbs', 4, 4);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (15, 'Natürliche Zahlen', 1, NULL);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (8, 'Holocaust', 6, 5);
INSERT INTO topic (id, description, course_id, topic_id) VALUES (9, 'Pearl Harbor', 6, 5);
SELECT pg_catalog.setval('topic_id_seq', 15, true);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (2, 4, 2);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (2, 1, 6);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (10, 2, 10);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (10, 7, 1);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (10, 8, 1);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (2, 3, 7);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (2, 5, 15);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (2, 6, 6);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (11, 10, 100);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (11, 13, 100);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (5, 5, 1);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (5, 8, 1);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (5, 9, 1);
INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (3, 9, 1);
ALTER TABLE ONLY ability_description
    ADD CONSTRAINT ability_description_pkey PRIMARY KEY (id);
ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);
ALTER TABLE ONLY assessment
    ADD CONSTRAINT assessment_pkey PRIMARY KEY (id);
ALTER TABLE ONLY assessment_student
    ADD CONSTRAINT assessment_student_pkey PRIMARY KEY (id);
ALTER TABLE ONLY assessment_type
    ADD CONSTRAINT assessment_type_pkey PRIMARY KEY (id);
ALTER TABLE ONLY class
    ADD CONSTRAINT class_pkey PRIMARY KEY (id);
ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);
ALTER TABLE ONLY mark
    ADD CONSTRAINT mark_pkey PRIMARY KEY (id);
ALTER TABLE ONLY mark_type
    ADD CONSTRAINT mark_type_pkey PRIMARY KEY (id);
ALTER TABLE ONLY student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);
ALTER TABLE ONLY teacher_class_course
    ADD CONSTRAINT teacher_class_course_pkey PRIMARY KEY (teacher_id, class_id, course_id);
ALTER TABLE ONLY teacher
    ADD CONSTRAINT teacher_pkey PRIMARY KEY (id);
ALTER TABLE ONLY topic
    ADD CONSTRAINT topic_pkey PRIMARY KEY (id);
ALTER TABLE ONLY mark
    ADD CONSTRAINT unique_bound_per_type UNIQUE (bound, mark_type_id, representation);
ALTER TABLE ONLY class
    ADD CONSTRAINT unique_class UNIQUE (level, stream);
ALTER TABLE ONLY topic
    ADD CONSTRAINT unique_per_course UNIQUE (description, course_id, topic_id);
ALTER TABLE ONLY ability_description
    ADD CONSTRAINT unique_per_description UNIQUE (description);
ALTER TABLE ONLY weighted_assessment
    ADD CONSTRAINT weighted_assessment_pkey PRIMARY KEY (main_assessment_id, sub_assessment_id);
CREATE UNIQUE INDEX class_stream_null_idx ON class USING btree (level) WHERE (stream IS NULL);
ALTER TABLE ONLY assessment
    ADD CONSTRAINT assessment_assessment_type_id_fkey FOREIGN KEY (assessment_type_id) REFERENCES assessment_type(id);
ALTER TABLE ONLY assessment_student
    ADD CONSTRAINT assessment_student_mark_id_fkey FOREIGN KEY (mark_id) REFERENCES mark(id);
ALTER TABLE ONLY assessment_student
    ADD CONSTRAINT assessment_student_student_id_fkey FOREIGN KEY (student_id) REFERENCES student(id);
ALTER TABLE ONLY assessment_student
    ADD CONSTRAINT assessment_student_weighted_assessment_main_id_fkey FOREIGN KEY (weighted_assessment_main_id, weighted_assessment_sub_id) REFERENCES weighted_assessment(main_assessment_id, sub_assessment_id);
ALTER TABLE ONLY assessment
    ADD CONSTRAINT assessment_topic_id_fkey FOREIGN KEY (topic_id) REFERENCES topic(id);
ALTER TABLE ONLY mark
    ADD CONSTRAINT mark_mark_type_id_fkey FOREIGN KEY (mark_type_id) REFERENCES mark_type(id);
ALTER TABLE ONLY student
    ADD CONSTRAINT student_ability_description_id_fkey FOREIGN KEY (ability_description_id) REFERENCES ability_description(id);
ALTER TABLE ONLY student
    ADD CONSTRAINT student_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE ONLY student
    ADD CONSTRAINT student_class_id_fkey FOREIGN KEY (class_id) REFERENCES class(id);
ALTER TABLE ONLY teacher
    ADD CONSTRAINT teacher_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE ONLY teacher_class_course
    ADD CONSTRAINT teacher_class_course_class_id_fkey FOREIGN KEY (class_id) REFERENCES class(id);
ALTER TABLE ONLY teacher_class_course
    ADD CONSTRAINT teacher_class_course_course_id_fkey FOREIGN KEY (course_id) REFERENCES course(id);
ALTER TABLE ONLY teacher_class_course
    ADD CONSTRAINT teacher_class_course_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES teacher(id);
ALTER TABLE ONLY topic
    ADD CONSTRAINT topic_course_id_fkey FOREIGN KEY (course_id) REFERENCES course(id);
ALTER TABLE ONLY topic
    ADD CONSTRAINT topic_topic_id_fkey FOREIGN KEY (topic_id) REFERENCES topic(id);
ALTER TABLE ONLY weighted_assessment
    ADD CONSTRAINT weighted_assessment_main_assessment_id_fkey FOREIGN KEY (main_assessment_id) REFERENCES assessment(id);
ALTER TABLE ONLY weighted_assessment
    ADD CONSTRAINT weighted_assessment_sub_assessment_id_fkey FOREIGN KEY (sub_assessment_id) REFERENCES assessment(id);
create or replace view assview AS
select weighted_assessment_main_id, weighted_assessment_sub_id from assessment_student
where mark_id is not null 
group by weighted_assessment_main_id, weighted_assessment_sub_id;