-- Updates: Edit this file and upload along with whost executable jar.
-- The startup script will execute all queries in this file prior to whost execution.
-- If done correctly, this ensures that tables existing from old create code will match
-- tables created in current code.

-- Add a column:
--ALTER TABLE USR.LOGON ADD ACTSTTS tinyint;

-- Update a specific value:
UPDATE USR.LOGON SET ACTSTTS = 10 WHERE USRNM = 'root';