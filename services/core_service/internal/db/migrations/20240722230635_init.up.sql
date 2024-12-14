CREATE TABLE groups (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(50) NOT NULL,
    
    teacher_id INTEGER
);

CREATE TABLE group_students (
    id SERIAL PRIMARY KEY,
    
    group_id INTEGER,
    student_id INTEGER,

    FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE subjects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description TEXT,
    color_hex VARCHAR(20)
);

CREATE TABLE lessons (
    id SERIAL PRIMARY KEY,
    
    group_id INTEGER,
    subject_id INTEGER,
    teacher_id INTEGER,
    
    date DATE,
    start_time TIMESTAMPTZ,
    end_time TIMESTAMPTZ,

    FOREIGN KEY (group_id) REFERENCES groups (id),
    FOREIGN KEY (subject_id) REFERENCES subjects (id)
);
