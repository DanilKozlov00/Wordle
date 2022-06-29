CREATE TABLE IF NOT EXISTS public.word
(
    id            bigint                                            NOT NULL DEFAULT nextval('word_id_seq'::regclass),
    dictionary_id smallint                                          NOT NULL,
    word          character varying(5) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT word_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.word
    OWNER to postgres;

