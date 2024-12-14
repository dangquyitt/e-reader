CREATE TABLE "users"
(
    "id"                BIGSERIAL PRIMARY KEY,
    "created_at"        timestamp      NOT NULL DEFAULT (now()),
    "updated_at"        timestamp      NOT NULL DEFAULT (now()),
    "email"             varchar UNIQUE NOT NULL,
    "email_verified_at" timestamp,
    "password"          varchar
);

CREATE TABLE "email_verifications"
(
    "id"                BIGSERIAL PRIMARY KEY,
    "created_at"        timestamp NOT NULL DEFAULT (now()),
    "updated_at"        timestamp NOT NULL DEFAULT (now()),
    "verification_code" varchar   NOT NULL,
    "email"             varchar   NOT NULL,
    "expired_at"        timestamp,
    "status"            varchar   NOT NULL DEFAULT 'PENDING'
);

CREATE TABLE "roles"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp      NOT NULL DEFAULT (now()),
    "updated_at" timestamp      NOT NULL DEFAULT (now()),
    "name"       varchar UNIQUE NOT NULL
);

CREATE TABLE "user_roles"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "user_id"    bigint    NOT NULL,
    "role_id"    bigint    NOT NULL
);

CREATE TABLE "permissions"
(
    "id"          BIGSERIAL PRIMARY KEY,
    "created_at"  timestamp NOT NULL DEFAULT (now()),
    "updated_at"  timestamp NOT NULL DEFAULT (now()),
    "http_method" varchar   NOT NULL,
    "path"        varchar   NOT NULL
);

CREATE TABLE "role_permissions"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "created_at"    timestamp NOT NULL DEFAULT (now()),
    "updated_at"    timestamp NOT NULL DEFAULT (now()),
    "role_id"       bigint    NOT NULL,
    "permission_id" bigint    NOT NULL
);

CREATE TABLE "books"
(
    "id"              BIGSERIAL PRIMARY KEY,
    "created_at"      timestamp NOT NULL DEFAULT (now()),
    "updated_at"      timestamp NOT NULL DEFAULT (now()),
    "title"           varchar   NOT NULL DEFAULT '',
    "description"     varchar   NOT NULL DEFAULT '',
    "totalPage"       int       NOT NULL DEFAULT 0,
    "rating"          float,
    "published_year"  int,
    "cover_image_url" varchar   NOT NULL,
    "file_url"        varchar   NOT NULL
);

CREATE TABLE "collections"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp      NOT NULL DEFAULT (now()),
    "updated_at" timestamp      NOT NULL DEFAULT (now()),
    "name"       varchar UNIQUE NOT NULL,
    "user_id"    bigint         NOT NULL
);

CREATE TABLE "book_collections"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "created_at"    timestamp NOT NULL DEFAULT (now()),
    "updated_at"    timestamp NOT NULL DEFAULT (now()),
    "book_id"       bigint,
    "collection_id" bigint
);

CREATE TABLE "ratings"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "user_id"    bigint    NOT NULL,
    "book_id"    bigint    NOT NULL,
    "rating"     float     NOT NULL DEFAULT 0
);

CREATE TABLE "reading_progresses"
(
    "id"                 BIGSERIAL PRIMARY KEY,
    "created_at"         timestamp NOT NULL DEFAULT (now()),
    "updated_at"         timestamp NOT NULL DEFAULT (now()),
    "user_id"            bigint    NOT NULL,
    "book_id"            bigint    NOT NULL,
    "last_read_position" int       NOT NULL DEFAULT 0,
    "progress"           float     NOT NULL DEFAULT 0,
    "reading_status"     varchar   NOT NULL DEFAULT 'IN_PROGRESS',
    "completed_at"       timestamp
);

CREATE TABLE "tags"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp      NOT NULL DEFAULT (now()),
    "updated_at" timestamp      NOT NULL DEFAULT (now()),
    "name"       varchar UNIQUE NOT NULL
);

CREATE TABLE "book_tags"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "book_id"    bigint,
    "tag_id"     bigint
);

CREATE TABLE "comments"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "user_id"    bigint    NOT NULL,
    "book_id"    bigint    NOT NULL,
    "content"    varchar   NOT NULL DEFAULT ''
);

CREATE TABLE "prices"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp      NOT NULL DEFAULT (now()),
    "updated_at" timestamp      NOT NULL DEFAULT (now()),
    "amount"     decimal(18, 2) NOT NULL DEFAULT 0,
    "currency"   varchar        NOT NULL DEFAULT 'USD',
    "plan_id"    bigint         NOT NULL
);

CREATE TABLE "plans"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "created_at"    timestamp NOT NULL DEFAULT (now()),
    "updated_at"    timestamp NOT NULL DEFAULT (now()),
    "name"          varchar   NOT NULL,
    "duration"      int       NOT NULL,
    "duration_unit" varchar   NOT NULL
);

CREATE TABLE "subscriptions"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "user_id"    bigint    NOT NULL,
    "price_id"   bigint    NOT NULL,
    "start_time" timestamp NOT NULL,
    "end_time"   timestamp NOT NULL
);

CREATE TABLE "favorites"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "user_id"    bigint    NOT NULL,
    "book_id"    bigint    NOT NULL
);

CREATE TABLE "authors"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp      NOT NULL DEFAULT (now()),
    "updated_at" timestamp      NOT NULL DEFAULT (now()),
    "name"       varchar UNIQUE NOT NULL
);

CREATE TABLE "book_authors"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "created_at" timestamp NOT NULL DEFAULT (now()),
    "updated_at" timestamp NOT NULL DEFAULT (now()),
    "book_id"    bigint    NOT NULL,
    "author_id"  bigint    NOT NULL
);

CREATE INDEX ON "email_verifications" ("email");

CREATE UNIQUE INDEX ON "user_roles" ("user_id", "role_id");

CREATE UNIQUE INDEX ON "permissions" ("http_method", "path");

CREATE UNIQUE INDEX ON "role_permissions" ("role_id", "permission_id");

CREATE UNIQUE INDEX ON "collections" ("user_id", "name");

CREATE UNIQUE INDEX ON "book_collections" ("book_id", "collection_id");

CREATE UNIQUE INDEX ON "ratings" ("user_id", "book_id");

CREATE UNIQUE INDEX ON "reading_progresses" ("user_id", "book_id");

CREATE UNIQUE INDEX ON "book_tags" ("book_id", "tag_id");

CREATE UNIQUE INDEX ON "favorites" ("user_id", "book_id");

CREATE UNIQUE INDEX ON "book_authors" ("book_id", "author_id");

ALTER TABLE "user_roles"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_roles"
    ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "role_permissions"
    ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "role_permissions"
    ADD FOREIGN KEY ("permission_id") REFERENCES "permissions" ("id");

ALTER TABLE "collections"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "book_collections"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_collections"
    ADD FOREIGN KEY ("collection_id") REFERENCES "collections" ("id");

ALTER TABLE "ratings"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "ratings"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "reading_progresses"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "reading_progresses"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_tags"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_tags"
    ADD FOREIGN KEY ("tag_id") REFERENCES "tags" ("id");

ALTER TABLE "comments"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "comments"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "prices"
    ADD FOREIGN KEY ("plan_id") REFERENCES "plans" ("id");

ALTER TABLE "subscriptions"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "subscriptions"
    ADD FOREIGN KEY ("price_id") REFERENCES "prices" ("id");

ALTER TABLE "favorites"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "favorites"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_authors"
    ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_authors"
    ADD FOREIGN KEY ("author_id") REFERENCES "authors" ("id");
