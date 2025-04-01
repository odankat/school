CREATE TABLE Persons (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 0),
    has_license BOOLEAN NOT NULL
);
CREATE TABLE Cars (
    car_id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);
CREATE TABLE Person_Car (
    person_id INT NOT NULL,
    car_id INT NOT NULL,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES Persons(person_id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES Cars(car_id) ON DELETE CASCADE
);

воспользовался ИИ для выполнения этого задания