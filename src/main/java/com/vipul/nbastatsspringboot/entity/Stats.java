package com.vipul.nbastatsspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName="stats", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    @JsonProperty("ast")
    @Field(type = FieldType.Long)
    private Long ast;
    @JsonProperty("blk")
    @Field(type = FieldType.Long)
    private Long blk;
    @JsonProperty("dreb")
    @Field(type = FieldType.Long)
    private Long dreb;
    @JsonProperty("fg3_pct")
    @Field(type = FieldType.Double)
    private Double fg3Pct;
    @JsonProperty("fg3a")
    @Field(type = FieldType.Long)
    private Long fg3a;
    @JsonProperty("fg3m")
    @Field(type = FieldType.Long)
    private Long fg3m;
    @JsonProperty("fg_pct")
    @Field(type = FieldType.Double)
    private Double fgPct;
    @JsonProperty("fga")
    @Field(type = FieldType.Long)
    private Long fga;
    @JsonProperty("fgm")
    @Field(type = FieldType.Long)
    private Long fgm;
    @JsonProperty("ft_pct")
    @Field(type = FieldType.Double)
    private Double ftPct;
    @JsonProperty("fta")
    @Field(type = FieldType.Long)
    private Long fta;
    @JsonProperty("ftm")
    @Field(type = FieldType.Long)
    private Long ftm;
    @JsonProperty("game")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Game game;
    @JsonProperty("id")
    @Field(type = FieldType.Long)
    private Long id;
    @JsonProperty("min")
    @Field(type = FieldType.Text)
    private String min;
    @JsonProperty("oreb")
    @Field(type = FieldType.Long)
    private Long oreb;
    @JsonProperty("pf")
    @Field(type = FieldType.Long)
    private Long pf;
    @JsonProperty("player")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Player player;
    @JsonProperty("pts")
    @Field(type = FieldType.Long)
    private Long pts;
    @JsonProperty("reb")
    @Field(type = FieldType.Long)
    private Long reb;
    @JsonProperty("stl")
    @Field(type = FieldType.Long)
    private Long stl;
    @JsonProperty("team")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Team team;
    @JsonProperty("turnover")
    @Field(type = FieldType.Long)
    private Long turnover;

}
