ALTER TABLE prices
    ADD COLUMN effective_date TIMESTAMP NOT NULL DEFAULT NOW();