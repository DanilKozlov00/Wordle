CREATE TABLE IF NOT EXISTS public.dictionary
(
    id   smallint                                   NOT NULL DEFAULT nextval('dictionary_id_seq'::regclass),
    name character(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dictionary_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.dictionary
    OWNER to postgres;

