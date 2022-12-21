
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName="player", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @JsonProperty("first_name")
    @Field(type = FieldType.Text)
    private String firstName;
    @JsonProperty("height_feet")
    @Field(type = FieldType.Long)
    private Long heightFeet;
    @JsonProperty("height_inches")
    @Field(type = FieldType.Long)
    @Id
    private Long heightInches;
    @JsonProperty("id")
    @Field(type = FieldType.Long)
    private Long id;
    @JsonProperty("last_name")
    @Field(type = FieldType.Text)
    private String lastName;
    @JsonProperty("position")
    @Field(type = FieldType.Text)
    private String position;
    @JsonProperty("team_id")
    @Field(type = FieldType.Long)
    private Long teamId;
    @JsonProperty("weight_pounds")
    @Field(type = FieldType.Long)
    private Long weightPounds;
}
