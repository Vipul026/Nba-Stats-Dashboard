package com.vipul.nbastatsspringboot.services;

import com.vipul.nbastatsspringboot.entity.Player;
import com.vipul.nbastatsspringboot.entity.PlayersData;
import com.vipul.nbastatsspringboot.repository.PlayerRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServices.class);

    @Value("${freenba.players.url}")
    private String playersUrl;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private RestTemplate restTemplate;

    private final String indexName = "player";

    public Player getPlayerInfo(Integer playerId) throws IOException {
        String uri = playersUrl + "/" + playerId;
        System.out.println(uri);
        Player player = restTemplate.getForObject(
                uri, Player.class);
        LOGGER.info(player.toString());

        return player;
    }

    public ResponseEntity<?> getAllPlayers() throws IOException {
        try {
            String uri = playersUrl;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

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
            playersData = restTemplate.getForObject(perPageData, PlayersData.class);
            allPlayers = playersData.getData();
            LOGGER.info(allPlayers.toString());

            playerRepository.saveAll(allPlayers);

            delayTask();
        }

        return "Players data added successfully";
    }

    //Finding Players from elastic search
    public SearchHits<Player> findPlayers(String firstName, String lastName){

        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("firstName", firstName))
                .must(QueryBuilders.matchQuery("lastName", lastName));

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        SearchHits<Player> players = template.search(nativeSearchQuery, Player.class);

        List<Player> list = new ArrayList<>();

        list = players.stream().map(e -> e.getContent()).collect(Collectors.toList());

        System.out.println(list);

        return players;
    }

    public List<Player> findByFirstName(String firstName) throws IOException {
        List<Player> players = playerRepository.findByFirstName(firstName);
        LOGGER.info(players.toString());
        return players;
    }


}
