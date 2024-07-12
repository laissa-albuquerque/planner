CREATE TABLE activities(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    activity_name VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    is_completed BOOLEAN NOT NULL,
    trip_id UUID,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);