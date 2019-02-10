--
-- Run this script against the DB to make sure you do not have
-- any teams in the tournament_participant table that do not match
-- the season data.
--
-- This should not be an issue, since I've cleaned up the data
-- up to 2016, but until then, the team names from the tournament 
-- results I had to manually enter (from Google search, etc) team
-- did not always match the team names from the downloaded data
-- from the NCAA web site.
--
-- For example, a common mismatch was ASU (from USA Today), when 
-- it should have been Arizona St (from NCAA data).
--
-- If you manually enter tournament results data after 2016
-- (i.e., after I plan to release this code into the wild), this
-- could be an issue for you, depending on where you get the data.
-- 
-- Check out the script correct_missing_team_names.sql, which
-- I used to fix this as I was getting all this lined out.
-- 
select distinct year, missing_team_name from (
select tp.year, tp.team_name as missing_team_name
from tournament_participant tp
    left outer join
     scoring_offense so on tp.team_name = so.team_name and tp.year = so.year
where so.team_name is null

union

select tr.year, tr.winning_team_name as missing_team_name
from tournament_result tr
    left outer join
     scoring_offense so on tr.winning_team_name = so.team_name and tr.year = so.year
where so.team_name is null

union

select tr.year, tr.losing_team_name as missing_team_name
from tournament_result tr
    left outer join
     scoring_offense so on tr.losing_team_name = so.team_name and tr.year = so.year
where so.team_name is null
) nested
order by 1, 2
;