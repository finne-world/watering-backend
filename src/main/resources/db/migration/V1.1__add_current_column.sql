ALTER TABLE devices ADD current BOOL AFTER name;
UPDATE devices SET current = 0 WHERE current is NULL;
ALTER TABLE devices MODIFY COLUMN current BOOL NOT NULL;
