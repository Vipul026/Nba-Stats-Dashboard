package com.vipul.nbastatsspringboot.repository;

import com.vipul.nbastatsspringboot.entity.Player;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ElasticsearchRepository<Player, Integer> {

}
