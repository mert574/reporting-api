package net.mert.reportingapi.model.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Data
public class TransactionRequest {

    @Max(32)
    @NotEmpty
    private String transactionId;

    private String apiKey;
}
