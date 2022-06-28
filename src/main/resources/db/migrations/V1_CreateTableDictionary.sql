CREATE SEQUENCE IF NOT EXISTS dictionary_id_seq;

CREATE TABLE IF NOT EXISTS "public"."dictionary"
(
    "id"   int2       NOT NULL DEFAULT nextval
        (
            'dictionary_id_seq'::regclass
        ),
    "name" bpchar(30) NOT NULL,
    PRIMARY KEY
        (
         "id"
            )
);

INSERT INTO public.dictionary(id, name)
VALUES (1, 'english');
INSERT INTO public.dictionary(id, name)
VALUES (2, 'russian');
