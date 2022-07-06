
INSERT INTO dictionary (name) VALUES ('english') ON CONFLICT DO NOTHING;

INSERT INTO word (dictionary_id, word) VALUES (1,'which') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'their') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'would') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'there') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'could') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'other') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'about') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'great') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'these') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'after') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'first') ON CONFLICT DO NOTHING;
INSERT INTO word (dictionary_id, word) VALUES (1,'words') ON CONFLICT DO NOTHING;

INSERT INTO "user"  (email, password, phone, name, nickname, role) VALUES ('admin@gmail.com', '$2a$12$sMoMVIRnKvFHHcT4KMS/FeTSn0P1NzgPm8wI7wuHpmzxRdGf9fjc6', '88005553535', 'admin', 'admin', 'ROLE_admin') ON CONFLICT DO NOTHING;


