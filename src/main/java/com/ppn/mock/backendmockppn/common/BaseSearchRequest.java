package com.ppn.mock.backendmockppn.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BaseSearchRequest {

    @NotNull
    private Integer pageIndex;

    @NotNull
    private Integer pageSize;

    private String sortBy;

    @JsonProperty
    private boolean isAscending;
}
