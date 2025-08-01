CREATE TABLE IF NOT EXISTS note_objects (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    parent_id BIGINT,
    type VARCHAR(255) NOT NULL DEFAULT 'FILE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES note_objects(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file_contents (
    id BIGSERIAL PRIMARY KEY,
    content TEXT,
    note_object_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (note_object_id) REFERENCES note_objects(id) ON DELETE CASCADE
);