SELECT 
    s.name AS student_name, 
    s.age AS student_age, 
    f.name AS faculty_name
FROM 
    Student s
JOIN 
    faculty f ON s.faculty_id = f.id;

SELECT
    s.name AS student_name,
    s.age AS student_age
FROM
    Student s
JOIN
    Avatar a ON a.student_id = s.id;