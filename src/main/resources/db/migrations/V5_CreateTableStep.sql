CREATE TABLE IF NOT EXISTS public.step
(
    "number"   smallint NOT NULL,
    attempt_id bigint   NOT NULL,
    id         bigint   NOT NULL DEFAULT nextval('step_id_seq'::regclass),
    CONSTRAINT step_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.step
    OWNER to postgres;