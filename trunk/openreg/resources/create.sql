
/*
Peter Moser peter.moser@stud-inf.unibz.it
Valentin Huber valentin.huber@stud-inf.unibz.it
Johannes Ganthaler johannes.ganthaler@stud-inf.unibz.it

ds_group2
*/

CREATE TABLE IF NOT EXISTS address
(
  id bigserial NOT NULL PRIMARY KEY, 
  street character varying NOT NULL,
  no character varying NOT NULL,
  zip_code character varying NOT NULL,
  city character varying NOT NULL,
  country character varying NOT NULL
);

CREATE TABLE IF NOT EXISTS class
(
  id bigserial NOT NULL PRIMARY KEY,
  level character varying NOT NULL,
  stream character varying,
  notes character varying,
  CONSTRAINT unique_class UNIQUE (level, stream)
);
CREATE UNIQUE INDEX class_stream_null_idx ON class (level) WHERE stream IS NULL;
 
CREATE TABLE IF NOT EXISTS teacher
(
  id bigserial NOT NULL PRIMARY KEY,
  name character varying NOT NULL,
  surname character varying NOT NULL,
  login character varying NOT NULL,
  password character varying NOT NULL,
  birthday date NOT NULL,
  address_id bigint NOT NULL REFERENCES address,
  phone_number character varying,
  picture bit varying,
  notes text
);

CREATE TABLE IF NOT EXISTS ability_description
(
  id bigserial NOT NULL PRIMARY KEY,
  description character varying NOT NULL,
  CONSTRAINT unique_per_description UNIQUE (description)
);

CREATE TABLE IF NOT EXISTS student
(
  id bigserial NOT NULL PRIMARY KEY,
  name character varying NOT NULL,
  surname character varying NOT NULL,
  birthday date NOT NULL,
  address_id bigint NOT NULL REFERENCES address,
  class_id bigint NOT NULL REFERENCES class,
  phonenumber character varying,
  enrolment_year int NOT NULL,
  ability_description_id bigint REFERENCES ability_description,  
  picture bit varying,
  notes character varying 
);

CREATE TABLE IF NOT EXISTS course
(
  id bigserial NOT NULL PRIMARY KEY,
  name character varying NOT NULL,
  credit_points int
);

CREATE TABLE IF NOT EXISTS teacher_class_course
(
  teacher_id bigint NOT NULL REFERENCES teacher,
  class_id bigint NOT NULL REFERENCES class,
  course_id bigint NOT NULL REFERENCES course,
  PRIMARY KEY (teacher_id, class_id, course_id)
);

CREATE TABLE IF NOT EXISTS assessment_type
(
  id bigserial PRIMARY KEY,
  description character varying NOT NULL
);

CREATE TABLE IF NOT EXISTS topic
(
  id bigserial PRIMARY KEY,
  description character varying NOT NULL,
  course_id bigint NOT NULL REFERENCES course,
  topic_id bigint REFERENCES topic,
  CONSTRAINT unique_per_course UNIQUE (description, course_id, topic_id)
);

CREATE TABLE IF NOT EXISTS assessment
(
  id bigserial PRIMARY KEY,
  assessment_type_id bigint NOT NULL REFERENCES assessment_type,
  topic_id bigint REFERENCES topic, 
  description character varying,
  notes character varying
);

CREATE TABLE IF NOT EXISTS weighted_assessment
(
  main_assessment_id bigint NOT NULL REFERENCES assessment,
  sub_assessment_id bigint NOT NULL REFERENCES assessment,
  weight float NOT NULL CHECK (weight > 0 AND weight <= 100),
  PRIMARY KEY (main_assessment_id, sub_assessment_id)
);

CREATE TABLE IF NOT EXISTS mark_type
(
  id bigserial PRIMARY KEY,
  description character varying NOT NULL
);

CREATE TABLE IF NOT EXISTS mark
(
  id bigserial PRIMARY KEY,
  representation character varying NOT NULL,
  bound float NOT NULL,
  CHECK (bound >= 0 AND bound <= 100),  -- We calculate everything in percentages...
  mark_type_id bigint NOT NULL REFERENCES mark_type,
  CONSTRAINT unique_bound_per_type UNIQUE(bound, mark_type_id, representation)
);

CREATE TABLE IF NOT EXISTS assessment_student
(
  id bigserial PRIMARY KEY,
  student_id bigint NOT NULL REFERENCES student,
  weighted_assessment_main_id bigint NOT NULL,
  weighted_assessment_sub_id bigint NOT NULL,
  FOREIGN KEY (weighted_assessment_main_id,weighted_assessment_sub_id) REFERENCES weighted_assessment (main_assessment_id, sub_assessment_id),
  mark_id bigint REFERENCES mark,
  mark float CHECK (mark >= 0 AND mark <= 100),
  date date NOT NULL,
  differentiated_evaluation boolean,
  notes character varying,
  CONSTRAINT mark_id_or_mark_not_both CHECK((mark_id IS NULL AND mark IS NOT NULL) OR (mark_id IS NOT NULL AND mark IS NULL))
);
