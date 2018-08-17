CREATE TABLE IF NOT EXISTS item (
    id              INTEGER PRIMARY KEY NOT NULL,
    brand_id        INTEGER NOT NULL,
    upc             TEXT NOT NULL,
    description     TEXT NOT NULL,
    when_created    INTEGER NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE INDEX item_idx1 ON item(description);