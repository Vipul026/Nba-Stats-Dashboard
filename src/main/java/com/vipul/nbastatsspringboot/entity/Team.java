
package com.vipul.nbastatsspringboot.entity;

import javax.annotation.Generated;

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
@Document(indexName="team", shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @JsonProperty("abbreviation")
    @Field(type = FieldType.Text)
    private String abbreviation;
    @JsonProperty("city")
    @Field(type = FieldType.Text)
    private String city;
    @JsonProperty("conference")
    @Field(type = FieldType.Text)
    private String conference;
    @JsonProperty("division")
    @Field(type = FieldType.Text)
    private String division;
    @JsonProperty("full_name")
    @Field(type = FieldType.Text)
    private String fullName;
    @JsonProperty("id")
    @Field(type = FieldType.Long)
    @Id
    private Long id;
    @JsonProperty("name")
    @Field(type = FieldType.Text)
    private String name;

}
