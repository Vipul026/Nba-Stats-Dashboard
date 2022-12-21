package com.vipul.nbastatsspringboot.repository;

import com.vipul.nbastatsspringboot.entity.Team;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends ElasticsearchRepository<Team, Long> {
}
