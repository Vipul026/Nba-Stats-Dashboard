
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
@Document(indexName="game", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @JsonProperty("date")
    @Field(type = FieldType.Text)
    private String date;
    @JsonProperty("home_team")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Team homeTeam;
    @JsonProperty("home_team_score")
    @Field(type = FieldType.Long)
    private Long homeTeamScore;
    @JsonProperty("id")
    @Field(type = FieldType.Long)
    private Long id;
    @JsonProperty("period")
    @Field(type = FieldType.Long)
    private Long period;
    @JsonProperty("postseason")
    @Field(type = FieldType.Boolean)
    private Boolean postseason;
    @JsonProperty("season")
    @Field(type = FieldType.Long)
    private Long season;
    @JsonProperty("abbreviation")
    @Field(type = FieldType.Text)
    private String status;
    @JsonProperty("status")
    @Field(type = FieldType.Text)
    private String time;
    @JsonProperty("visitor_team")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Team visitorTeam;
    @JsonProperty("visitor_team_score")
    @Field(type = FieldType.Long)
    private Long visitorTeamScore;
}
