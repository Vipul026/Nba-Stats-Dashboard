package com.vipul.nbastatsspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName="all_teams", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamsData {
    @JsonProperty("data")
    private List<Team> data;
    @JsonProperty("meta")
    private Meta meta;
}
