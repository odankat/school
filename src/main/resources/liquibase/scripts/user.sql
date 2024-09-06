-- liquibase formatted sql

-- changeset jrembo:1
CREATE INDEX student_name_index ON student (name);
-- changeset jrembo:2
CREATE INDEX faculty_nc_index ON faculty (name, color);


