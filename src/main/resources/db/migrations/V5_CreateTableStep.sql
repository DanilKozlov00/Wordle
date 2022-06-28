CREATE SEQUENCE step_id_seq;

CREATE TABLE IF NOT EXISTS "public"."step"
(
    "number"     int2 NOT NULL,
    "attempt_id" int8 NOT NULL,
    "id"         int8 NOT NULL DEFAULT nextval('step_id_seq'::regclass),
    CONSTRAINT "attempt" FOREIGN KEY ("attempt_id") REFERENCES "public"."attempt" ("id"),
    PRIMARY KEY ("id")
);