CREATE TABLE users (
    id SERIAL PRIMARY KEY, 
    surname VARCHAR(150) NOT NULL,
    name VARCHAR(150) NOT NULL,
    patronymic VARCHAR(150) NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL, 
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ
);
