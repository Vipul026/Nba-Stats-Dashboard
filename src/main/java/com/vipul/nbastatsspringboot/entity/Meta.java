package com.vipul.nbastatsspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    @JsonProperty("current_page")
    private Long currentPage;
    @JsonProperty("next_page")
    private Long nextPage;
    @JsonProperty("per_page")
    private Long perPage;
    @JsonProperty("total_count")
    private Long totalCount;
    @JsonProperty("total_pages")
    private Long totalPages;

}
