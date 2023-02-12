package com.vipul.nbastatsspringboot.controllers;

import com.vipul.nbastatsspringboot.services.StatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatController {

    @Autowired
    private StatServices statServices;

    @GetMapping("/save-data")
    public ResponseEntity<?> addAllData() throws InterruptedException {
        return new ResponseEntity<>(statServices.addAllStats(), HttpStatus.OK);
    }

    @GetMapping("/get-points")
    public ResponseEntity<?> getPointsOfPlayer(@RequestParam Long season, @RequestParam int playerId){
        return new ResponseEntity<>(statServices.compareStatsOfPlayers(season, playerId), HttpStatus.OK);
    }
}
