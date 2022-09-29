CREATE FUNCTION get_user_no ()
    RETURNS bigint
    LANGUAGE 'plpgsql'
    COST 100 VOLATILE PARALLEL UNSAFE
    AS $BODY$
DECLARE
    unique_no bigint;
BEGIN
    SELECT
        EXTRACT(epoch FROM CURRENT_TIMESTAMP(4)) * 10000 INTO unique_no;
    RETURN unique_no;
END;
$BODY$;

ALTER TABLE USERS
    ALTER user_no SET DEFAULT get_user_no ();

