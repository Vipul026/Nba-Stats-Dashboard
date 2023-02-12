package com.vipul.nbastatsspringboot.services;

import com.vipul.nbastatsspringboot.entity.Stats;
import com.vipul.nbastatsspringboot.entity.StatsData;
import com.vipul.nbastatsspringboot.repository.StatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StatServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServices.class);

    @Value("${freenba.stats.url}")
    private String statsUrl;

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String addAllStats() throws InterruptedException {
        String uri = statsUrl;

        StatsData statsData = restTemplate.getForObject(uri, StatsData.class);
        LOGGER.info(statsData.toString());

        List<Stats> stats = statsData.getData();
//        System.out.println(stats);

        statRepository.saveAll(stats);

        Long totalPages = statsData.getMeta().getTotalPages();
        System.out.println(totalPages);

        for(long i=2;i<=totalPages;i++){
            String perPageData = uri + "?page=" + i;
            statsData = restTemplate.getForObject(perPageData, StatsData.class);

            stats = statsData.getData();
            LOGGER.info(statsData.toString());

            statRepository.saveAll(stats);

            Thread.sleep(1000);
        }
        return "Data added successfully";
    }

    public String compareStatsOfPlayers(Long season, int player1){


        return "hello";
    }
}
