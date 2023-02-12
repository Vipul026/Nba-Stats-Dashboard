package com.vipul.nbastatsspringboot.services;

import com.vipul.nbastatsspringboot.entity.Game;
import com.vipul.nbastatsspringboot.entity.GameData;
import com.vipul.nbastatsspringboot.repository.GameRepository;
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

    @Autowired
    private GameRepository gameRepository;

    public ResponseEntity<?> getAllGames(){
        try{
            String uri = gamesUrl;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

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

            GameData result = restTemplate.getForObject(uri, GameData.class);
            LOGGER.info(result.toString());

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
    public String saveAllGamesData() throws InterruptedException {
        String uri = gamesUrl;
        System.out.println(uri);

        GameData gameData = restTemplate.getForObject(uri, GameData.class);
        LOGGER.info(gameData.toString());

        List<Game> games = gameData.getData();

        gameRepository.saveAll(games);

        Long totalPages = gameData.getMeta().getTotalPages();

        for(long i=2;i<=totalPages;i++){
            String perPageData = uri + "?page=" + i;
            gameData = restTemplate.getForObject(perPageData, GameData.class);
            games = gameData.getData();
            LOGGER.info(games.toString());

            gameRepository.saveAll(games);
            Thread.sleep(2000);
        }

        return "Game data added successfully";
    }

    //Method for finding the season champions
    public String getSeasonChampions(Long season){

        List<Game> allGames = gameRepository.findBySeason(season);

        List<Date> dates = new ArrayList<>();

        for(Game game: allGames){
            dates.add(game.getDate());
        }

        Collections.sort(dates);

        System.out.println(dates.get(dates.size()-1));
        Date dateofLastGame = dates.get(dates.size() - 1);

        Game lastGameofSeason = gameRepository.findByDate(dateofLastGame);

        if(lastGameofSeason.getHomeTeamScore() > lastGameofSeason.getVisitorTeamScore())
            return lastGameofSeason.getHomeTeam().getFullName();

        return lastGameofSeason.getVisitorTeam().getFullName();
    }

    //Season performance in consecutive seasons
    public String getGamesWonByTeamInSeason(Long season, String teamName){

        List<Game> allGames = gameRepository.findBySeason(season);

        int noOfWins = 0, noOfLoss = 0;

        for(Game game: allGames){
            if(game.getHomeTeam().getName().equals(teamName)){
                if(game.getHomeTeamScore() > game.getVisitorTeamScore())
                    noOfWins++;
                else
                    noOfLoss++;
            }
            else if(game.getVisitorTeam().getName().equals(teamName)){
                if(game.getVisitorTeamScore() > game.getHomeTeamScore()){
                    noOfWins++;
                }
                else{
                    noOfLoss++;
                }
            }
        }

        System.out.println(noOfWins+" "+noOfLoss);

        return noOfWins+" "+noOfLoss;
    }

    public Map<Long, String> getTeamPerformanceInVariousSeasons(Long fromSeason, Long toSeason, String teamName){
        Map<Long, String> seasonStat = new HashMap<>();
        int noOfWins, noOfLoss;

        for(long i = fromSeason; i<=toSeason; i++){
            List<Game> allGames = gameRepository.findBySeason(i);
            noOfWins = 0; noOfLoss = 0;

            for(Game game: allGames){

                if(game.getHomeTeam().getName().equals(teamName)){
                    if(game.getHomeTeamScore() > game.getVisitorTeamScore())
                        noOfWins++;
                    else
                        noOfLoss++;
                }
                else if(game.getVisitorTeam().getName().equals(teamName)){
                    if(game.getVisitorTeamScore() > game.getHomeTeamScore()){
                        noOfWins++;
                    }
                    else{
                        noOfLoss++;
                    }
                }
                String teamStat = noOfWins+" "+noOfLoss;
                seasonStat.put(i, teamStat);
            }
        }
        return seasonStat;
    }
}
