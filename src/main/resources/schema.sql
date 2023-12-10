-- Drop tables if they already exist
--DROP TABLE IF EXISTS user_events;
--DROP TABLE IF EXISTS events;
--DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

-- Create events table
CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    organiser_id INT REFERENCES users(id),
    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Create user_events junction table
CREATE TABLE IF NOT EXISTS user_events (
    user_id INT REFERENCES users(id),
    event_id INT REFERENCES events(id),
    PRIMARY KEY (user_id, event_id)
);

INSERT INTO users (username)
SELECT 'alice'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'alice');

INSERT INTO users (username)
SELECT 'bob'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'bob');

INSERT INTO users (username)
SELECT 'charlie'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'charlie');

