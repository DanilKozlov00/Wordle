DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'character_position') THEN
            CREATE TYPE "public"."character_position" AS ENUM ('CORRECT_POSITION', 'INCORRECT_POSITION', 'MISSING_IN_WORD');
        END IF;
    END
$$;
CREATE TABLE IF NOT EXISTS public.word_character
(
    id                 bigint             NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    character_position character_position NOT NULL,
    step_id            bigint             NOT NULL,
    "character"        "char"             NOT NULL,
    CONSTRAINT word_character_pkey PRIMARY KEY (id),
    CONSTRAINT step FOREIGN KEY (step_id)
        REFERENCES public.step (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.word_character
    OWNER to postgres;