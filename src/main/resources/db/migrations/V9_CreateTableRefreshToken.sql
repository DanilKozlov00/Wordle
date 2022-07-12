CREATE TABLE IF NOT EXISTS public.refresh_token
(
    id           bigint                                              NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 2 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    user_id      bigint                                              NOT NULL,
    token        character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "expiryDate" timestamp without time zone                         NOT NULL,
    CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.refresh_token
    OWNER to postgres;