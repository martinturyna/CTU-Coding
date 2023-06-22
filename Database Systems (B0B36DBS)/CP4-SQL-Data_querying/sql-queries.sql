/* --- Vsechny queries uspesne otestovany --- */

/* Vybere vsechny profily, ktere maji nadraftovany tym Real Madrid */
SELECT profile_id
FROM draft INNER JOIN team ON (team_name = name)
WHERE  name = 'Real Madrid';

/* Vybere vsechny tymy, ktere nesjou prirazene k nejakemu profilu a seradi je podle abecedy */
SELECT name
FROM Team
EXCEPT
SELECT team_name
FROM draft
ORDER BY name ASC; 

/* Vybere vsechny ligy (divize), kterych se muze zucastnit vice jak 10 hracu
- participant_count udava pocet hracu, ktery se divize muze zucastnit */
SELECT name
FROM division
GROUP BY name
HAVING (participant_count > 10)
ORDER BY name ASC; 

/* Vybere ID profilu hracu, kteri jiz odehrali v lize zapas jako "domaci" (team1 DOMA, team2 HOST) */
SELECT profile_id
FROM draft
WHERE team_name IN (SELECT team1_name
					FROM match);

/* Vybere vsechny goal_id z golu, ktere strelil dany tym (V tomto pripade Chelsea) */
SELECT goal_id
FROM goal
  NATURAL JOIN match
WHERE goal.team_name = 'Chelsea'; 