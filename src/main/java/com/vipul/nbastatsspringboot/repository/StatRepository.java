package com.vipul.nbastatsspringboot.repository;

import com.vipul.nbastatsspringboot.entity.Stats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatRepository extends ElasticsearchRepository<Stats, Long> {
    List<Stats> findByGameSeason(Long Season);
}
