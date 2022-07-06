CREATE TABLE IF NOT EXISTS public.step
(
    "number"   smallint NOT NULL,
    attempt_id bigint   NOT NULL,
    id         bigint   NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    CONSTRAINT step_pkey PRIMARY KEY (id),
    CONSTRAINT attempt FOREIGN KEY (attempt_id)
        REFERENCES public.attempt (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.step
    OWNER to postgres;