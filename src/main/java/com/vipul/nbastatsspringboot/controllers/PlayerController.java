package com.vipul.nbastatsspringboot.controllers;

import com.sun.org.apache.regexp.internal.RE;
import com.vipul.nbastatsspringboot.services.PlayerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<?> getAllPlayers(){
        return new ResponseEntity<>(playerServices.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchPlayer(@PathVariable("name") String name){
        return new ResponseEntity<>(playerServices.searchPlayer(name), HttpStatus.OK);
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
