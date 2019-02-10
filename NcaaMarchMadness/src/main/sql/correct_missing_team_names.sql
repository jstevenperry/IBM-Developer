--
-- Corrects any team names that do not match their counterparts from
-- the downloaded NCAA data.
--
-- Depending on where you get the data, this will be more or less
-- of an issue, but it will always be an issue because you have to
-- manually enter tournament_result data (in the form of a load
-- script of course).
--
-- Sadly, the NCAA website does not have a download just for the
-- tournament results (maybe they should).
-- 
-- Connecticut -> UConn
UPDATE tournament_result SET winning_team_name = 'UConn' WHERE winning_team_name = 'Connecticut';

UPDATE tournament_result SET losing_team_name = 'UConn' WHERE losing_team_name = 'Connecticut';

-- Florida State -> Florida St
UPDATE tournament_result SET winning_team_name = 'Florida St' WHERE winning_team_name = 'Florida State';

UPDATE tournament_result SET losing_team_name = 'Florida St' WHERE losing_team_name = 'Florida State';

-- Long Island -> LIU Brooklyn
UPDATE tournament_result SET winning_team_name = 'LIU Brooklyn' WHERE winning_team_name = 'Long Island';

UPDATE tournament_result SET losing_team_name = 'LIU Brooklyn' WHERE losing_team_name = 'Long Island';

-- UALR -> Little Rock
UPDATE tournament_result SET winning_team_name = 'Little Rock' WHERE winning_team_name = 'UALR';

UPDATE tournament_result SET losing_team_name = 'Little Rock' WHERE losing_team_name = 'UALR';

-- North Fla -> North Florida
UPDATE tournament_result SET winning_team_name = 'North Florida' WHERE winning_team_name = 'North Fla';

UPDATE tournament_result SET losing_team_name = 'North Florida' WHERE losing_team_name = 'North Fla';

-- Albany -> Albany NY
UPDATE tournament_result SET winning_team_name = 'Albany NY' WHERE winning_team_name = 'Albany';

UPDATE tournament_result SET losing_team_name = 'Albany NY' WHERE losing_team_name = 'Albany';

-- Coastal Carolina -> Coastal Caro
UPDATE tournament_result SET winning_team_name = 'Coastal Caro' WHERE winning_team_name = 'Coastal Carolina';

UPDATE tournament_result SET losing_team_name = 'Coastal Caro' WHERE losing_team_name = 'Coastal Carolina';

-- Temmessee -> Tennessee (WTF?)
UPDATE tournament_result SET winning_team_name = 'Tennessee' WHERE winning_team_name = 'Temmessee';

UPDATE tournament_result SET losing_team_name = 'Tennessee' WHERE losing_team_name = 'Temmessee';

-- East Tenn St -> ETSU
UPDATE tournament_result SET winning_team_name = 'ETSU' WHERE winning_team_name = 'East Tenn St';

UPDATE tournament_result SET losing_team_name = 'ETSU' WHERE losing_team_name = 'East Tenn St';

-- Cal St Northridge -> CSUN
UPDATE tournament_result SET winning_team_name = 'CSUN' WHERE winning_team_name = 'Cal St Northridge';

UPDATE tournament_result SET losing_team_name = 'CSUN' WHERE losing_team_name = 'Cal St Northridge';

-- Miami Fla -> Miami FL
UPDATE tournament_result SET winning_team_name = 'Miami FL' WHERE winning_team_name = 'Miami Fla';

UPDATE tournament_result SET losing_team_name = 'Miami FL' WHERE losing_team_name = 'Miami Fla';

-- St Marys Cal -> St Marys CA
UPDATE tournament_result SET winning_team_name = 'St Marys CA' WHERE winning_team_name = 'St Marys Cal';

UPDATE tournament_result SET losing_team_name = 'St Marys CA' WHERE losing_team_name = 'St Marys Cal';

-- St Marys CA -> Saint Marys CA
UPDATE tournament_result SET winning_team_name = 'Saint Marys CA' WHERE winning_team_name = 'St Marys CA';

UPDATE tournament_result SET losing_team_name = 'Saint Marys CA' WHERE losing_team_name = 'St Marys CA';

-- St Josephs -> Saint Josephs
UPDATE tournament_result SET winning_team_name = 'Saint Josephs' WHERE winning_team_name = 'St Josephs';

UPDATE tournament_result SET losing_team_name = 'Saint Josephs' WHERE losing_team_name = 'St Josephs';  

-- South Alabama -> South Ala
UPDATE tournament_result SET winning_team_name = 'South Ala' WHERE winning_team_name = 'South Alabama';

UPDATE tournament_result SET losing_team_name = 'South Ala' WHERE losing_team_name = 'South Alabama';  

-- TexasArlington -> UT Arlington
UPDATE tournament_result SET winning_team_name = 'UT Arlington' WHERE winning_team_name = 'TexasArlington';

UPDATE tournament_result SET losing_team_name = 'UT Arlington' WHERE losing_team_name = 'TexasArlington';  

-- Stephen F Austin -> SFA
UPDATE tournament_result SET winning_team_name = 'SFA' WHERE winning_team_name = 'Stephen F Austin';

UPDATE tournament_result SET losing_team_name = 'SFA' WHERE losing_team_name = 'Stephen F Austin';  

-- Detroit -> Detroit Mercy
UPDATE tournament_result SET winning_team_name = 'Detroit Mercy' WHERE winning_team_name = 'Detroit' and year < 2016;

UPDATE tournament_result SET losing_team_name = 'Detroit Mercy' WHERE losing_team_name = 'Detroit' and year < 2016;  

-- North Carolina St -> NC State
UPDATE tournament_result SET winning_team_name = 'NC State' WHERE winning_team_name = 'North Carolina St' and year < 2016;

UPDATE tournament_result SET losing_team_name = 'NC State' WHERE losing_team_name = 'North Carolina St' and year < 2016;  

-- Lamar -> Lamar University
UPDATE tournament_result SET winning_team_name = 'Lamar University' WHERE winning_team_name = 'Lamar';

UPDATE tournament_result SET losing_team_name = 'Lamar University' WHERE losing_team_name = 'Lamar';  

-- La-Lafayette -> Lafayette
UPDATE tournament_result SET winning_team_name = 'Lafayette' WHERE winning_team_name = 'La-Lafayette';

UPDATE tournament_result SET losing_team_name = 'Lafayette' WHERE losing_team_name = 'La-Lafayette';  

