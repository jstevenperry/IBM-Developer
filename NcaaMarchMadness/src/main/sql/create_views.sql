--
-- Create all views for the statistical categories for the tournament
-- participants. These views are helpful when pulling data since we really
-- only care about the stats of tournament participants
--
CREATE VIEW v_won_lost_percentage AS SELECT foo.* FROM won_lost_percentage foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_scoring_offense AS SELECT foo.* FROM scoring_offense foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;
    
CREATE VIEW v_scoring_defense AS SELECT foo.* FROM scoring_defense foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_scoring_margin AS SELECT foo.* FROM scoring_margin foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_field_goal_percentage AS SELECT foo.* FROM field_goal_percentage foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_field_goal_percentage_defense AS SELECT foo.* FROM field_goal_percentage_defense foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_three_point_field_goals_per_game AS SELECT foo.* FROM three_point_field_goals_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_three_point_percentage AS SELECT foo.* FROM three_point_percentage foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_three_point_percentage_defense AS SELECT foo.* FROM three_point_percentage_defense foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_free_throw_percentage AS SELECT foo.* FROM free_throw_percentage foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_rebound_margin AS SELECT foo.* FROM rebound_margin foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_assists_per_game AS SELECT foo.* FROM assists_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_assist_turnover_ratio AS SELECT foo.* FROM assist_turnover_ratio foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_blocked_shots_per_game AS SELECT foo.* FROM blocked_shots_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_steals_per_game AS SELECT foo.* FROM steals_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_turnovers_per_game AS SELECT foo.* FROM turnovers_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_turnover_margin AS SELECT foo.* FROM turnover_margin foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

CREATE VIEW v_personal_fouls_per_game AS SELECT foo.* FROM personal_fouls_per_game foo
    JOIN tournament_participant tp ON foo.team_name = tp.team_name AND foo.year = tp.year;

--
-- Create the views for aggregating data
--
-- 
-- Offense
--
CREATE VIEW v_offense AS
SELECT 
  vso.year, vso.team_name, vso.avg_points_per_game,
  vsm.scoring_margin_per_game,
  vfgp.num_fg_attempts_per_game, vfgp.fg_percentage,
  vtpfgpg.num_3p_per_game,
  vtpp.num_3p_attempts_per_game, vtpp.t3p_percentage,
  vftp.num_ft_attempts_per_game, vftp.ft_percentage,
  vrm.rebound_margin,
  vapg.assists_per_game,
  vato.ato_ratio
FROM v_scoring_offense vso 
  JOIN
     v_scoring_margin vsm ON vso.team_name = vsm.team_name AND vso.year = vsm.year 
  JOIN
     v_field_goal_percentage vfgp ON vso.team_name = vfgp.team_name AND vso.year = vfgp.year
  JOIN
     v_three_point_field_goals_per_game vtpfgpg ON vso.team_name = vtpfgpg.team_name AND vso.year = vtpfgpg.year
  LEFT OUTER JOIN
     v_three_point_percentage vtpp ON vso.team_name = vtpp.team_name AND vso.year = vtpp.year
  JOIN
     v_free_throw_percentage vftp ON vso.team_name = vftp.team_name AND vso.year = vftp.year
  JOIN
     v_rebound_margin vrm ON vso.team_name = vrm.team_name AND vso.year = vrm.year
  JOIN
     v_assists_per_game vapg ON vso.team_name = vapg.team_name AND vso.year = vapg.year
  JOIN
     v_assist_turnover_ratio vato ON vso.team_name = vato.team_name AND vso.year = vato.year
;
  
--
-- Defense
--
CREATE VIEW v_defense AS
SELECT vsd.year, vsd.team_name, vsd.avg_opponent_points_per_game,
       vfgpd.num_opp_fg_attempts_per_game, vfgpd.opp_fg_percentage,
       vtppd.num_opp_3p_attempts_per_game, vtppd.opp_3p_percentage,
       vbspg.blocks_per_game,
       vspg.steals_per_game,
       vtm.opp_turnovers_per_game
FROM v_scoring_defense vsd
    JOIN
     v_field_goal_percentage_defense vfgpd ON vsd.team_name = vfgpd.team_name AND vsd.year = vfgpd.year
    JOIN
     v_three_point_percentage_defense vtppd ON vsd.team_name = vtppd.team_name AND vsd.year = vtppd.year
    JOIN
     v_blocked_shots_per_game vbspg ON vsd.team_name = vbspg.team_name AND vsd.year = vbspg.year
    JOIN
     v_steals_per_game vspg ON vsd.team_name = vspg.team_name AND vsd.year = vspg.year
    LEFT OUTER JOIN
     v_turnover_margin vtm ON vsd.team_name = vtm.team_name AND vsd.year = vtm.year
;

--
-- Errors
--
CREATE VIEW v_errors AS
SELECT vtpg.year, vtpg.team_name, vtpg.turnovers_per_game,
       vpfpg.fouls_per_game, vpfpg.num_dq
FROM v_turnovers_per_game vtpg
    JOIN
     v_personal_fouls_per_game vpfpg ON vtpg.team_name = vpfpg.team_name AND vtpg.year = vpfpg.year
;

--
-- Giant aggreate view - pulls all the views together
--
CREATE VIEW v_season_data AS
SELECT vo.year, 
       vo.team_name, 
       vo.avg_points_per_game,
       vo.scoring_margin_per_game,
       vo.num_fg_attempts_per_game, 
       vo.fg_percentage,
       vo.num_3p_per_game,
       vo.num_3p_attempts_per_game, 
       vo.t3p_percentage,
       vo.num_ft_attempts_per_game, 
       vo.ft_percentage,
       vo.rebound_margin,
       vo.assists_per_game,
       vo.ato_ratio,
       vd.avg_opponent_points_per_game,
       vd.num_opp_fg_attempts_per_game, 
       vd.opp_fg_percentage,
       vd.num_opp_3p_attempts_per_game, 
       vd.opp_3p_percentage,
       vd.blocks_per_game,
       vd.steals_per_game,
       vd.opp_turnovers_per_game,
       ve.turnovers_per_game,
       ve.fouls_per_game,
       ve.num_dq
FROM v_offense vo
    JOIN
     v_defense vd ON vo.team_name = vd.team_name AND vo.year = vd.year
    JOIN
     v_errors ve ON ve.team_name = vo.team_name AND vo.year = ve.year
;

CREATE VIEW v_season_analytics AS
SELECT year,
       min(avg_points_per_game) AS min_avg_points_pg,
       max(avg_points_per_game) AS max_avg_points_pg,
       min(scoring_margin_per_game) AS min_scoring_margin_pg,
       max(scoring_margin_per_game) AS max_scoring_margin_pg,
       min(num_fg_attempts_per_game) AS min_num_fg_attempts_pg,
       max(num_fg_attempts_per_game) AS max_num_fg_attempts_pg,
       min(fg_percentage) AS min_fg_percentage,
       max(fg_percentage) AS max_fg_percentage,
       min(num_3p_per_game) AS min_num_3p_per_game,
       max(num_3p_per_game) AS max_num_3p_per_game,
       min(num_3p_attempts_per_game) AS min_num_3p_attempts_pg,
       max(num_3p_attempts_per_game) AS max_num_3p_attempts_pg,
       min(t3p_percentage) AS min_t3p_percentage,
       max(t3p_percentage) AS max_t3p_percentage,
       min(num_ft_attempts_per_game) AS min_num_ft_attempts_pg,
       max(num_ft_attempts_per_game) AS max_num_ft_attempts_pg,
       min(ft_percentage) AS min_ft_percentage,
       max(ft_percentage) AS max_ft_percentage,
       min(rebound_margin) AS min_rebound_margin,
       max(rebound_margin) AS max_rebound_margin,
       min(assists_per_game) AS min_assists_pg,
       max(assists_per_game) AS max_assists_pg,
       min(ato_ratio) AS min_ato_ratio,
       max(ato_ratio) AS max_ato_ratio,
       min(avg_opponent_points_per_game) AS min_avg_opponent_points_pg,
       max(avg_opponent_points_per_game) AS max_avg_opponent_points_pg,
       min(num_opp_fg_attempts_per_game) AS min_num_opp_fg_attempts_pg,
       max(num_opp_fg_attempts_per_game) AS max_num_opp_fg_attempts_pg,
       min(opp_fg_percentage) AS min_opp_fg_percentage,
       max(opp_fg_percentage) AS max_opp_fg_percentage,
       min(num_opp_3p_attempts_per_game) AS min_num_opp_3p_attempts_pg,
       max(num_opp_3p_attempts_per_game) AS max_num_opp_3p_attempts_pg,
       min(opp_3p_percentage) AS min_opp_3p_percentage,
       max(opp_3p_percentage) AS max_opp_3p_percentage,
       min(blocks_per_game) AS min_blocks_pg,
       max(blocks_per_game) AS max_blocks_pg,
       min(steals_per_game) AS min_steals_pg,
       max(steals_per_game) AS max_steals_pg,
       min(opp_turnovers_per_game) AS min_opp_turnovers_pg,
       max(opp_turnovers_per_game) AS max_opp_turnovers_pg,
       min(turnovers_per_game) AS min_turnovers_pg,
       max(turnovers_per_game) AS max_turnovers_pg,
       min(fouls_per_game) AS min_fouls_pg,
       max(fouls_per_game) AS max_fouls_pg,
       min(num_dq) AS min_num_dq,
       max(num_dq) AS max_num_dq
FROM v_season_data
GROUP BY 1;

CREATE VIEW v_tournament_analytics AS
SELECT year,
       min(losing_score) AS min_score,
       max(winning_score) AS max_score
FROM tournament_result 
GROUP BY 1;
;
--
-- Create views for normalizing the data
--
--CREATE VIEW vn_won_lost_percentage AS
--select win_percentage, ((win_percentage - min(win_percentage))/(max(win_percentage)-min(win_percentage))) as max_wlp from v_won_lost_percentage group by 1
--;  
--select win_percentage, ((win_percentage - (select min(win_percentage) from v_won_lost_percentage))/((select max(win_percentage) from v_won_lost_percentage)-(select min(win_percentage) from v_won_lost_percentage))) as max_wlp from v_won_lost_percentage group by 1
    
    
    
    
    