DROP TABLE IF EXISTS "trophy";
DROP TABLE IF EXISTS "draft";
DROP TABLE IF EXISTS "profile";
DROP TABLE IF EXISTS "User";
DROP TABLE IF EXISTS "goal";
DROP TABLE IF EXISTS "match";
DROP TABLE IF EXISTS "team";
DROP TABLE IF EXISTS "firstleague";
DROP TABLE IF EXISTS "otherleague";
DROP TABLE IF EXISTS "division";
CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(20) NOT NULL UNIQUE,
    facebook VARCHAR(128) NOT NULL,
    password VARCHAR(50) NOT NULL,
    friend_id INTEGER UNIQUE CONSTRAINT user_friend_id_fk REFERENCES "User",
    CONSTRAINT email_check CHECK (email LIKE '_%@_%.__%')
);
CREATE TABLE profile (
    ingame_nick VARCHAR(20) NOT NULL,
    platform VARCHAR(5) NOT NULL,
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE CONSTRAINT profile_user_id_fk REFERENCES "User"
);
CREATE TABLE trophy (
    name VARCHAR(50) NOT NULL,
    profile_id INTEGER NOT NULL CONSTRAINT trophy_profile_id_fk REFERENCES profile,
    PRIMARY KEY (profile_id)
);
CREATE TABLE division (
    name VARCHAR(50) PRIMARY KEY UNIQUE,
    participant_count INTEGER  CHECK (participant_count >= 0)
);
CREATE TABLE team (
    name VARCHAR(30) PRIMARY KEY UNIQUE,
    division_name VARCHAR(50) NOT NULL CONSTRAINT team_division_name_fk REFERENCES division
);
CREATE TABLE draft (
    profile_id INTEGER NOT NULL CONSTRAINT trophy_profile_id_fk REFERENCES profile,
    team_name VARCHAR(30) NOT NULL CONSTRAINT draft_team_name_fk REFERENCES team,
    PRIMARY KEY(profile_id, team_name)
);
CREATE TABLE match (
    match_id SERIAL PRIMARY KEY,
    division_name VARCHAR(50) NOT NULL CONSTRAINT match_division_name_fk REFERENCES division,
    team1_name VARCHAR(30) NOT NULL CONSTRAINT match_team1_name_fk REFERENCES team,
    team2_name VARCHAR(30) NOT NULL CONSTRAINT match_team2_name_fk REFERENCES team
);
CREATE TABLE goal (
    goal_id SERIAL PRIMARY KEY,
    team_name VARCHAR(30) NOT NULL CONSTRAINT goal_team_name_fk REFERENCES team,
    match_id INTEGER NOT NULL CONSTRAINT goal_match_id_fk REFERENCES match,
    goal_time TIME NOT NULL
);
CREATE TABLE firstleague (
    division_name VARCHAR(50) NOT NULL CONSTRAINT firstleague_division_name_fk REFERENCES division,
    stream_url VARCHAR(128),
    PRIMARY KEY (division_name)
);
CREATE TABLE otherleague (
    division_name VARCHAR(50) NOT NULL CONSTRAINT otherleague_division_name_fk REFERENCES division,
    PRIMARY KEY(division_name)
);