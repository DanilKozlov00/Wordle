CREATE SEQUENCE IF NOT EXISTS word_character_id_seq;
DROP TYPE IF EXISTS "public"."character_position";
CREATE TYPE "public"."character_position" AS ENUM ('CORRECT_POSITION', 'INCORRECT_POSITION', 'MISSING_IN_WORD');

CREATE TABLE IF NOT EXISTS "public"."word_character"
(
    "id"                 int8                          NOT NULL DEFAULT nextval('word_character_id_seq'::regclass),
    "character_position" "public"."character_position" NOT NULL,
    "step_id"            int8                          NOT NULL,
    "character"          char                          NOT NULL,
    CONSTRAINT "step" FOREIGN KEY ("step_id") REFERENCES "public"."step" ("id"),
    PRIMARY KEY ("id")
);