package net.mert.reportingapi.model;

import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class TransactionReport {
    private Long count, total;

    @Max(3)
    private String currency;
}
