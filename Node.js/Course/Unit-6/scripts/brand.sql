CREATE TABLE IF NOT EXISTS brand(
    id              INTEGER PRIMARY KEY NOT NULL,
    description     TEXT NOT NULL,
    manufacturer    TEXT,
    location        TEXT,
    website         TEXT,
    when_created    INTEGER NOT NULL DEFAULT CURRENT_TIMESTAMP
);