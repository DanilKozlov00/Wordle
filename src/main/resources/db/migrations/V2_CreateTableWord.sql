CREATE SEQUENCE IF NOT EXISTS word_id_seq;

CREATE TABLE IF NOT EXISTS "public"."word"
(
    "id"            int8       NOT NULL DEFAULT nextval
        (
            'word_id_seq'::regclass
        ),
    "dictionary_id" int2       NOT NULL,
    "word"          varchar(5) NOT NULL,
    CONSTRAINT "dictionary" FOREIGN KEY
        (
         "dictionary_id"
            ) REFERENCES "public"."dictionary"
            (
             "id"
                ),
    PRIMARY KEY
        (
         "id"
            )
);


INSERT INTO public.word(dictionary_id, word)
VALUES (1, 'words');
INSERT INTO public.word(dictionary_id, word)
VALUES (2, 'слова');
