package com.vipul.nbastatsspringboot.controllers;

import com.vipul.nbastatsspringboot.services.GameServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameServices gameServices;

    @GetMapping("/all-games")
    public ResponseEntity<?> getAllGames(){
        return new ResponseEntity<>(gameServices.getAllGames(), HttpStatus.OK);
    }

    @GetMapping("/all-games/{season}")
    public ResponseEntity<?> getAllGamesOfASeason(@PathVariable("season")Integer season){
        return new ResponseEntity<>(gameServices.getAllGamesOfASeason(season), HttpStatus.OK);
    }

    @GetMapping("/team-games/{id}")
    public ResponseEntity<?> getAllGamesOfATeam(@PathVariable("id") Integer id){
        return new ResponseEntity<>(gameServices.getAllGamesOfATeam(id), HttpStatus.OK);
    }

    @GetMapping("/winning-team/{gameId}")
    public String getWinningTeam(@PathVariable("gameId") Integer id){
        return gameServices.getWinningTeam(id);
    }

    @GetMapping("/champ-team/{date}")
    public String getWinTeamForDate(@PathVariable("date") String date){
        return gameServices.getGameOnAParticularDate(date);
    }

    @GetMapping("/season-champions/{season}")
    public ResponseEntity<?> seasonChampions(@PathVariable("season") Long season){
        return new ResponseEntity<>(gameServices.getSeasonChampions(season), HttpStatus.OK);
    }

    @GetMapping("/save-data")
    public ResponseEntity<?> saveAllGamesData() throws InterruptedException {
        return new ResponseEntity<>(gameServices.saveAllGamesData(), HttpStatus.OK);
    }

    @GetMapping("/games-won")
    public ResponseEntity<?> getGamesWonByTeamInSeason(@RequestParam Long season, @RequestParam String teamName){
        return new ResponseEntity<>(gameServices.getGamesWonByTeamInSeason(season, teamName), HttpStatus.OK);
    }

    @GetMapping("/team-stat")
    public ResponseEntity<Map<Long,String>> getTeamPerformanceInVariousSeasons(@RequestParam Long fromSeason, @RequestParam Long toSeason, @RequestParam String teamName){
       return new ResponseEntity<>(gameServices.getTeamPerformanceInVariousSeasons(fromSeason, toSeason, teamName), HttpStatus.OK);
    }
}
