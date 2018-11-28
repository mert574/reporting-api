package net.mert.reportingapi.model.response;

import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class TransactionListResponse {

    private int per_page, current_page;

    @Max(256)
    private String next_page_url, prev_page_url;

    private Integer from, to;
}
