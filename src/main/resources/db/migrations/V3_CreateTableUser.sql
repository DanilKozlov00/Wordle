DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'roles') THEN
            CREATE TYPE "public"."roles" AS ENUM ('ROLE_admin', 'ROLE_user');
        END IF;
    END
$$;

CREATE TABLE IF NOT EXISTS public."user"
(
    id           bigint                                              NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    email        character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    password     character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone        character varying(12) COLLATE pg_catalog."default"  NOT NULL,
    name         character varying(30) COLLATE pg_catalog."default"  NOT NULL,
    nickname     character varying(30) COLLATE pg_catalog."default",
    balance      bigint                                              NOT NULL DEFAULT 0,
    role         roles                                               NOT NULL,
    statistic_id bigint                                              NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT statistic FOREIGN KEY (statistic_id)
        REFERENCES public.user_statistic (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;