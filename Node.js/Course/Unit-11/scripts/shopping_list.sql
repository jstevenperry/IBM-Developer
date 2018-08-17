CREATE TABLE IF NOT EXISTS shopping_list(
    id              INTEGER PRIMARY KEY NOT NULL,
    description     TEXT NOT NULL,
    when_created    INTEGER NOT NULL DEFAULT CURRENT_TIMESTAMP,
    when_modified   INTEGER
);