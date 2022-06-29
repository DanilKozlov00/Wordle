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
    id       bigint                                              NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    email    character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone    character varying(12) COLLATE pg_catalog."default"  NOT NULL,
    name     character varying(30) COLLATE pg_catalog."default"  NOT NULL,
    nickname character varying(30) COLLATE pg_catalog."default",
    balance  bigint                                              NOT NULL DEFAULT 0,
    role     roles                                               NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;
