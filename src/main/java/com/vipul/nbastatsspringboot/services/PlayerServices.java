package com.vipul.nbastatsspringboot.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.vipul.nbastatsspringboot.entity.Player;
import com.vipul.nbastatsspringboot.entity.PlayersData;
import com.vipul.nbastatsspringboot.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServices.class);

    @Value("${freenba.players.url}")
    private String playersUrl;

    @Autowired
    private PlayerRepository playerRepository;

//    @Autowired
//    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private RestTemplate restTemplate;

    private final String indexName = "player";

    public Player getPlayerInfo(Integer playerId) throws IOException {
        String uri = playersUrl + "/" + playerId;
        System.out.println(uri);
        Player player = restTemplate.getForObject(
                uri, Player.class);
        LOGGER.info(player.toString());

        return playerRepository.save(player);

       // return player.toString();

        //System.out.println(playerRepository.findById(playerId));


//        IndexResponse response = elasticsearchClient.index(i -> i
//                .index(indexName)
//                .id(playerId.toString())
//                .document(player)
//        );
//
//        if(response.result().name().equals("Created")){
//            return new StringBuilder("Document has been successfully created.").toString();
//        }else if(response.result().name().equals("Updated")){
//            return new StringBuilder("Document has been successfully updated.").toString();
//        }
//        return new StringBuilder("Error while performing the operation.").toString();
    }

    public ResponseEntity<?> getAllPlayers(){
        try {
            String uri = playersUrl;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

//            JSONObject jsonObject= new JSONObject(result);
//
//            List<Player> listPlayers = jsonObject;

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> searchPlayer(String name){
        try {
            String uri = playersUrl + "?search=" + name;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getPlayersOnPage(Integer pageNo){
        try{
            String uri = playersUrl + "?page=" + pageNo;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error! Please Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Elastic Search Methods
    @Scheduled(fixedRate = 1000)
    public static void delayTask() throws InterruptedException {
        Thread.sleep(1000);
    }

    public String savingAllPlayerData() throws InterruptedException {
        String uri = playersUrl;
        System.out.println(uri);

        PlayersData playersData = restTemplate.getForObject(uri, PlayersData.class);
        LOGGER.info(playersData.toString());

        List<Player> allPlayers = playersData.getData();
        LOGGER.info(allPlayers.toString());

        playerRepository.saveAll(allPlayers);

        Long totalPages = playersData.getMeta().getTotalPages();
        System.out.println(totalPages);

        for(long i=2; i<=totalPages; i++){
            String perPageData = uri + "?page=" + i;
//            System.out.println(perPageData);
            playersData = restTemplate.getForObject(perPageData, PlayersData.class);
            allPlayers = playersData.getData();
            LOGGER.info(allPlayers.toString());

            playerRepository.saveAll(allPlayers);

            delayTask();
        }

//        System.out.println(allPlayers);

        return "Players data added successfully";
    }
}
