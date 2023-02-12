package com.vipul.nbastatsspringboot.controllers;

import com.vipul.nbastatsspringboot.services.PlayerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerServices playerServices;

    @GetMapping("/{playerId}")
    public ResponseEntity<Object> getPlayerInfo(@PathVariable("playerId") Integer playerId) throws IOException {
        return new ResponseEntity<>(playerServices.getPlayerInfo(playerId), HttpStatus.OK);
    }


    @GetMapping("/all-players")
    public ResponseEntity<?> getAllPlayers() throws IOException {
        return new ResponseEntity<>(playerServices.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPlayer(@RequestParam String firstName, @RequestParam String lastName){
        return new ResponseEntity<>(playerServices.findPlayers(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("/search/{firstName}")
    public ResponseEntity<?> searchPlayer(@PathVariable("firstName") String firstName) throws IOException {
        return new ResponseEntity<>(playerServices.findByFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping("/page/{page_no}")
    public ResponseEntity<?> getPlayersOnPage(@PathVariable("page_no") Integer pageNo){
        return new ResponseEntity<>(playerServices.getPlayersOnPage(pageNo), HttpStatus.OK);
    }

    @GetMapping("/save-data")
    public ResponseEntity<?> saveAllPlayersData() throws InterruptedException {
        return new ResponseEntity<>(playerServices.savingAllPlayerData(), HttpStatus.OK);
    }
}
