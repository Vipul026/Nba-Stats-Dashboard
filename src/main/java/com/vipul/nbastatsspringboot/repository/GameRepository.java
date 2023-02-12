package com.vipul.nbastatsspringboot.repository;

import com.vipul.nbastatsspringboot.entity.Game;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GameRepository extends ElasticsearchRepository<Game, Long> {
    List<Game> findBySeason(Long season);

    Game findByDate(Date date);

}
