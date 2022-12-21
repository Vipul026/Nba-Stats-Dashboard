package com.vipul.nbastatsspringboot.services;

import com.vipul.nbastatsspringboot.entity.Game;
import com.vipul.nbastatsspringboot.entity.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GameServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServices.class);

    @Value("${freenba.games.url}")
    private String gamesUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> getAllGames(){
        try{
            String uri = gamesUrl;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

//            String[] arr = result.split(",");
//            for(int i=0;i< arr.length;i++){
//                System.out.println(arr[i]);
//            }

//            JSONArray arr = new JSONArray(result);
//
//            for(int i=0;i<arr.length();i++){
//                JSONObject obj = new JSONObject(i);
//                System.out.println(obj.getString("id"));
//                System.out.println(obj.getString("date"));
//            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllGamesOfASeason(Integer season){
        try {

            String uri = gamesUrl + "?seasons[]=" + season;
            System.out.println(uri);

//            String result = restTemplate.getForObject(uri, String.class);
//            LOGGER.info(result);

            GameData result = restTemplate.getForObject(uri, GameData.class);
            LOGGER.info(result.toString());

//            List<Datum> data = result.getData();
//            System.out.println(data.size());

//            List<String> dates = new ArrayList<>();
//            Collections.sort(dates);
//            System.out.println(dates.get(dates.size() - 1));

//            for(Datum value: data){
//                dates.add(value.getDate());
//            }
//            System.out.println(dates);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllGamesOfATeam(Integer id){
        try{
            String uri = gamesUrl + "?team_ids[]=" + id;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

            //System.out.println(winningTeamOfAGame());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getWinningTeam(Integer id){
        String uri = gamesUrl + "/" + id;
        System.out.println(uri);

        Game game = restTemplate.getForObject(uri, Game.class);
        LOGGER.info(game.toString());

        if(game.getHomeTeamScore() > game.getVisitorTeamScore())
            return game.getHomeTeam().getFullName();
        return game.getVisitorTeam().getFullName();
    }

    public String getGameOnAParticularDate(String date){
        String uri = gamesUrl + "?dates[]=" + date;
        System.out.println(uri);

        GameData game = restTemplate.getForObject(uri, GameData.class);
        LOGGER.info(game.getData().toString());

        List<Game> list = game.getData();
        System.out.println(list.size());

        Game data = list.get(1);
        System.out.println(data);

        if(data.getHomeTeamScore() > data.getVisitorTeamScore())
            return data.getHomeTeam().getFullName();

        return data.getVisitorTeam().getFullName();
    }

    //Finding the season champion
    public ResponseEntity<?> getSeasonChampion(Integer season) {
        try {
            String uri = gamesUrl + "?seasons[]=" + season;
            System.out.println(uri);

            GameData result = restTemplate.getForObject(uri, GameData.class);
            LOGGER.info(result.toString());

            List<Game> data = result.getData();

            List<String> dates = new ArrayList<>();

            Long totalPages = result.getMeta().getTotalPages();
            System.out.println(totalPages);

            System.out.println(dates.size());

            Collections.sort(dates);

            System.out.println(dates);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Elastic Search Methods
    public void saveAllGamesData(){

    }
}
