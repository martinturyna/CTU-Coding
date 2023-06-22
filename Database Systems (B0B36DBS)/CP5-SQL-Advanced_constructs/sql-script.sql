DROP VIEW IF EXISTS best_players;
DROP INDEX IF EXISTS drafts_with_team;
DROP TRIGGER IF EXISTS points_amount ON "User";
DROP FUNCTION IF EXISTS reward_player();
DROP FUNCTION IF EXISTS check_points();

/* Vytvoří novou tabulku nejlepších hráčů, kteří získali za aktivitu a výsledky více jak 100 bodů. 
Nejedná se o body získané za zápasy nýbrž o rank systém.  */
CREATE VIEW best_players AS
 	SELECT *
	FROM "User"
	WHERE points >= 100; 
/* V případě vyhledávání všech draftů, které mají konkrétní tým. Lze využít například pro
statistiky: porovnání výsledků všech hráčů, kteří hrají se stejným týmem. Urychlý vyhledávání pokud 
máme v databázi spoustu hráčů se stejným týmem */
CREATE INDEX drafts_with_team
	ON draft (team_name); 
/* Odměníme určitého hráče body do ranking systému. Ranking systém však umožňuje
 dosáhnout maximálního počtu bodů 1000. */
CREATE FUNCTION reward_player(pid INT, pts INT)
RETURNS INT
AS $$
	DECLARE 
		currpts INT;
	BEGIN
		currpts := (SELECT points FROM "User" WHERE (id = pid));
		IF ((currpts + pts) > 1000) 
		THEN 
			UPDATE "User" SET points = 1000 WHERE (id = pid);
			RETURN 1000; 
		END IF;
		UPDATE "User" SET points = points + pts WHERE (id = pid);
		RETURN (SELECT points from "User" WHERE (id = pid));
	END;
$$
LANGUAGE plpgsql;
/* Jestliže chceme odměnit hráče body, ale body jsou NULL nebo v záporu (něco je špatně), tak
necháme vypsat hlášku, že body mají špatnou hodnotu a nelze je tak přičíst. */
CREATE FUNCTION check_points()
RETURNS TRIGGER
AS $$
	IF ((NEW.points IS NULL) OR (NEW.points < 0)) THEN
		RAISE EXCEPTION 'Invalid points value';
	END IF;
	RETURN NEW;
$$
LANGUAGE plpgsql;

CREATE TRIGGER points_amount
  BEFORE INSERT OR UPDATE OF points
  on "User"
  FOR EACH ROW EXECUTE PROCEDURE check_points();

