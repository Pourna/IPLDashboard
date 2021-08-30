package io.ipldashboard.controller;

import io.ipldashboard.Repository.MatchRepository;
import io.ipldashboard.Repository.TeamRepository;
import io.ipldashboard.model.Match;
import io.ipldashboard.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam (@PathVariable  String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName,4));
        return team;
    }

    @GetMapping("/team")
    public Iterable<Team> getAllTeams() {
        return this.teamRepository.findAll();
    }

    @GetMapping("team/{teamName}/matches")
    public List<Match> getMatch (@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year+1,1,1);
        return  this.matchRepository.getMatchesByTeamBetweenDates(
                teamName, startDate, endDate);
    }
}
