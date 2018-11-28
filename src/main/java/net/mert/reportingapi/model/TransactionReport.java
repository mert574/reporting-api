package net.mert.reportingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReport {
    private Long count, total;

    @Max(3)
    private String currency;
}
