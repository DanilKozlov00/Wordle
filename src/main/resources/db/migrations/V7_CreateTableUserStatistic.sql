CREATE TABLE IF NOT EXISTS public.user_statistic
(
    id          bigint   NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    games_count bigint   NOT NULL DEFAULT 0,
    wins        bigint   NOT NULL DEFAULT 0,
    first_win   smallint NOT NULL DEFAULT 0,
    second_win  smallint NOT NULL DEFAULT 0,
    third_win   smallint NOT NULL DEFAULT 0,
    four_win    smallint NOT NULL DEFAULT 0,
    five_win    smallint NOT NULL DEFAULT 0,
    six_win     smallint NOT NULL DEFAULT 0,
    CONSTRAINT user_statistic_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_statistic
    OWNER to postgres;