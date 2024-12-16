ALTER TABLE prices
    ADD COLUMN features TEXT[] DEFAULT '{}'::text[],
    ADD COLUMN duration_unit VARCHAR NOT NULL,
    ADD COLUMN duration INT4 NOT NULL,
    ADD COLUMN metadata JSONB DEFAULT '{}'::jsonb NOT NULL;

ALTER TABLE plans
DROP COLUMN duration_unit,
DROP COLUMN duration;