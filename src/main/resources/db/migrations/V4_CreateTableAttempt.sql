CREATE SEQUENCE IF NOT EXISTS attempt_id_seq;

CREATE TABLE IF NOT EXISTS "public"."attempt"
(
    "id"               int8 NOT NULL DEFAULT nextval('attempt_id_seq'::regclass),
    "date"             date NOT NULL,
    "user"             int8 NOT NULL,
    "coins_win"        int2 NOT NULL DEFAULT 0,
    "is_admin_accrued" bool NOT NULL,
    "is_win"           bool NOT NULL,
    CONSTRAINT "user" FOREIGN KEY ("user") REFERENCES "public"."user" ("id"),
    PRIMARY KEY ("id")
);