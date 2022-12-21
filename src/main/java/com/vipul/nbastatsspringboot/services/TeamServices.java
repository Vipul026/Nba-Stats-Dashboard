package com.vipul.nbastatsspringboot.services;

import com.vipul.nbastatsspringboot.entity.Team;
import com.vipul.nbastatsspringboot.entity.TeamsData;
import com.vipul.nbastatsspringboot.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TeamServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServices.class);

    @Value("${freenba.teams.url}")
    private String teamsUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<?> getAllTeam(){
        try {
            String uri = teamsUrl;
            System.out.println(uri);

            String result = restTemplate.getForObject(uri, String.class);
            LOGGER.info(result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> searchTeam(Integer id){
        String uri = teamsUrl + "/" + id;
        System.out.println(uri);

        Team team = restTemplate.getForObject(uri, Team.class);
        LOGGER.info(team.toString());

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    //Elastic Search Methods
    public String savingAllTeamsData(){
        String uri = teamsUrl;
        System.out.println(uri);

        TeamsData teamsData = restTemplate.getForObject(uri, TeamsData.class);
        LOGGER.info(teamsData.toString());

        List<Team> allTeams = teamsData.getData();
        System.out.println(allTeams.size());

        teamRepository.saveAll(allTeams);

        return "Data Added Successfully";
    }
}
