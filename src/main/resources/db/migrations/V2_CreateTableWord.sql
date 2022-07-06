CREATE TABLE IF NOT EXISTS public.word
(
    id            bigint                                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    dictionary_id smallint                                          NOT NULL,
    word          character varying(5) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT word_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.word
    OWNER to postgres;

