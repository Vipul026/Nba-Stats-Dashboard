
package com.vipul.nbastatsspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName="game", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @JsonProperty("date")
    @Field(type = FieldType.Date)
    private Date date;
    @JsonProperty("home_team")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Team homeTeam;
    @JsonProperty("home_team_score")
    @Field(type = FieldType.Long)
    private Long homeTeamScore;
    @JsonProperty("id")
    @Field(type = FieldType.Long)
    @Id
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

//    public Game(String date, Team homeTeam, Long homeTeamScore, Long id, Long period, Boolean postseason, Long season, String status, String time, Team visitorTeam, Long visitorTeamScore) throws ParseException {
//        this.date = stringToDate(date);
//        this.homeTeam = homeTeam;
//        this.homeTeamScore = homeTeamScore;
//        this.id = id;
//        this.period = period;
//        this.postseason = postseason;
//        this.season = season;
//        this.status = status;
//        this.time = time;
//        this.visitorTeam = visitorTeam;
//        this.visitorTeamScore = visitorTeamScore;
//    }
//
//    private Date stringToDate(String date) throws ParseException {
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        Date local = inputFormat.parse(date);
//        return local;
//    }
}
