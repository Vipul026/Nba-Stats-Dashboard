package com.vipul.nbastatsspringboot.controllers;

import com.vipul.nbastatsspringboot.services.TeamServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/teams")
public class TeamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamServices teamServices;

    @GetMapping("/all-teams")
    public ResponseEntity<?> getAllTeams(){
        return new ResponseEntity<>(teamServices.getAllTeam(), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchTeam(@PathVariable("id") Integer id){
        return new ResponseEntity<>(teamServices.searchTeam(id), HttpStatus.OK);
    }

    @GetMapping("/all-data")
    public ResponseEntity<?> savingAllTeamsData(){
        return new ResponseEntity<>(teamServices.savingAllTeamsData(), HttpStatus.OK);
    }
}
