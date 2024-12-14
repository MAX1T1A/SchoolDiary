CREATE TYPE user_role AS ENUM ('STUDENT', 'TEACHER', 'ADMIN');

CREATE TABLE users (
    id SERIAL PRIMARY KEY, 
    surname VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100) NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role user_role NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
