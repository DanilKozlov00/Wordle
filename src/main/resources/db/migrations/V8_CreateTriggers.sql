CREATE OR REPLACE FUNCTION public.attempt_stat_func()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS
$BODY$
BEGIN
    IF ((SELECT COUNT(*) FROM user_statistic where user_id = new.user) = 0) THEN
        INSERT INTO user_statistic (user_id) VALUES (new.user);
    END IF;
    IF (new.is_win = true) THEN
        UPDATE user_statistic SET wins = wins + 1 where user_id = new.user;
    END IF;
    UPDATE user_statistic SET games_count = games_count + 1 where user_id = new.user;
    RETURN NEW;
END ;
$BODY$;

ALTER FUNCTION public.attempt_stat_func()
    OWNER TO postgres;

CREATE TRIGGER update_stat
    AFTER INSERT
    ON public.attempt
    FOR EACH ROW
EXECUTE FUNCTION public.attempt_stat_func();

CREATE OR REPLACE FUNCTION public.step_stat_func()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS
$BODY$
DECLARE
    steps          int;
    DECLARE userId bigint;
BEGIN
    SELECT INTO steps COUNT(*) FROM step where step.attempt_id = new.attempt_id;
    SELECT INTO userId attempt.user FROM attempt where attempt.id = new.attempt_id;

    IF (steps = 1) THEN
        UPDATE user_statistic SET wins = wins + 1, first_win = first_win + 1 where user_id = userId;
    END IF;

    IF (steps = 2) THEN
        UPDATE user_statistic SET wins = wins + 1, second_win = second_win + 1 where user_id = userId;
    END IF;

    IF (steps = 3) THEN
        UPDATE user_statistic SET wins = wins + 1, third_win = third_win + 1 where user_id = userId;
    END IF;

    IF (steps = 4) THEN
        UPDATE user_statistic SET wins = wins + 1, four_win = four_win + 1 where user_id = userId;
    END IF;

    IF (steps = 5) THEN
        UPDATE user_statistic SET wins = wins + 1, five_win = five_win + 1 where user_id = userId;
    END IF;

    IF (steps = 6) THEN
        UPDATE user_statistic SET wins = wins + 1, six_win = six_win + 1 where user_id = userId;
    END IF;
    RETURN NEW;
END ;

$BODY$;

ALTER FUNCTION public.step_stat_func()
    OWNER TO postgres;

CREATE TRIGGER test
    AFTER INSERT
    ON public.step
    FOR EACH ROW
EXECUTE FUNCTION public.step_stat_func();


