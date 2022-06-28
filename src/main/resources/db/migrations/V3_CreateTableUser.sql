CREATE SEQUENCE IF NOT EXISTS user_id_seq;
DROP TYPE IF EXISTS "public"."roles";
CREATE TYPE "public"."roles" AS ENUM ('ROLE_admin', 'ROLE_user');

CREATE TABLE IF NOT EXISTS "public"."user"
(
    "id"       int8             NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    "email"    varchar(50)      NOT NULL,
    "password" varchar(255)     NOT NULL,
    "phone"    varchar(12)      NOT NULL,
    "name"     varchar(30)      NOT NULL,
    "nickname" varchar(30),
    "balance"  int8             NOT NULL DEFAULT 0,
    "role"     "public"."roles" NOT NULL,
    PRIMARY KEY ("id")
);

INSERT INTO public."user"(id, email, password, phone, name, nickname, balance, role)
VALUES (2, 'admin@gmail.com', 'admin', '88005556565', 'admin', 'admin', '0', 'ROLE_admin');