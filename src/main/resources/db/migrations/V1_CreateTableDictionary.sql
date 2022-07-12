CREATE TABLE IF NOT EXISTS public.dictionary
(
    id   smallint                                   NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 32767 CACHE 1 ),
    name character(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dictionary_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.dictionary
    OWNER to postgres;

