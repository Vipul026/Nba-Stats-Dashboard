package com.vipul.nbastatsspringboot.repository;

import com.vipul.nbastatsspringboot.entity.Player;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends ElasticsearchRepository<Player, Integer> {
    List<Player> findByFirstName(String name);

    List<Player> findByFirstNameAndLastName(String firstName, String lastName);
}
